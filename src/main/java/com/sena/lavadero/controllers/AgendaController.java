package com.sena.lavadero.controllers;

import com.itextpdf.text.DocumentException;
import com.sena.lavadero.entities.*;
import com.sena.lavadero.enums.RolNombre;
import com.sena.lavadero.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static com.sena.lavadero.service.PdfService.TICKET_DIRECTORY;

@Controller
@RequestMapping("/agenda")
@SessionAttributes("servicio")
public class AgendaController {

    @Autowired
    private IAgendaService iAgendaService;

    @Autowired
    private IServiciosService iServiciosService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ITipoServiciosService iTipoServiciosService;

    @Autowired
    private IVehiculoService iVehiculoService;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/agenda-form/{id}")
    public String agendaForm(@PathVariable Long id, Model model, Authentication auth) {
        // OBTENEMOS USUARIO LOGUEADO
        if (auth != null) {
            String username = auth.getName();
            model.addAttribute("username", username);
        }
        // OBTENEMOS EL SERVICIO SELECCIONADO
        Servicio servicio = iServiciosService.servicioPorId(id);
        if (servicio != null) {
            System.out.println("**** SERVICIO ID EN GET **** " + servicio.getId());
        } else {
            System.out.println("**** SERVICIO NO ENCONTRADO ****");
        }

        List<Usuario> lavadores = usuarioService.getUsuariosPorRol(RolNombre.ROLE_LAVADOR);
        model.addAttribute("lavadores", lavadores);

        model.addAttribute("agenda", new Agenda());
        model.addAttribute("servicio", servicio);
        model.addAttribute("lavadores", lavadores);

        return "agenda/agendaForm";
    }

    @PostMapping("/save-agenda")
    public String guardarAgenda(@ModelAttribute Agenda agenda,
                                @ModelAttribute Servicio servicio,
                                @RequestParam("tipoServicioId") Long tipoServicioId,
                                RedirectAttributes redirect,
                                Model model,
                                Authentication auth) throws DocumentException, IOException {

        // OBTENEMOS USUARIO LOGUEADO
        if (auth != null) {
            String username = auth.getName();
            Usuario usuarioLogueado = usuarioService.getByUsername(username).orElse(null);
            if (usuarioLogueado != null) {
                agenda.setUsuarioCrea(usuarioLogueado);
            }
        }

        // CONSULTAMOS SERVICIO Y TIPO SERVICIO
        Servicio ser = iServiciosService.servicioPorId(servicio.getId());
        TipoServicio tipoServicioSeleccionado = iTipoServiciosService.obtenerTipo(tipoServicioId);

        String ticketNumber = UUID.randomUUID().toString();

        // VALIDAMOS DISPONIBILIDAD DE AGENDA
        if(!iAgendaService.agendarServicio(agenda.getFechaAgenda())) {
            model.addAttribute("agenda", agenda);
            System.out.println("**** NO HAY DISPONIBILIDAD ****");
            model.addAttribute("disponibilidadAgenda", "No hay disponibilidad de agenda para esta fecha!");
            return "agenda/agendaForm";
        }

        // VALIDACIÓN DE VEHÍCULO POR PLACA
        Vehiculo nuevoVehiculo = agenda.getVehiculo();  // El vehículo viene desde el formulario
        Vehiculo vehiculoExistente = iVehiculoService.vehiculoPorPlaca(nuevoVehiculo.getPlacaVehiculo());

        if (vehiculoExistente != null) {
            // Si el vehículo ya está registrado, lo asignamos a la agenda
            System.out.println("**** VEHÍCULO EXISTENTE **** ID: " + vehiculoExistente.getId());
            agenda.setVehiculo(vehiculoExistente);
        } else {
            // Si el vehículo no está registrado, lo guardamos y luego lo asignamos a la agenda
            System.out.println("**** VEHÍCULO NUEVO, REGISTRANDO... ****");
            iVehiculoService.save(nuevoVehiculo);
            agenda.setVehiculo(nuevoVehiculo);
        }

        agenda.setServicio(servicio);
        agenda.setTipoServicio(tipoServicioSeleccionado);
        agenda.setNumeroTicket(ticketNumber);
        iAgendaService.save(agenda);

        // GENERAR EL PDF
        String fileName = pdfService.generateTicketPdf(agenda);

        redirect.addFlashAttribute("saveAgenda", "Agenda Guardada Exitosamente!");
        redirect.addFlashAttribute("ticketFileName", fileName);
        System.out.println("**** FILENAME **** " + fileName);
        return "redirect:/";
    }


    @GetMapping("/download-ticket/{fileName}")
    public ResponseEntity<Resource> downloadTicket(@PathVariable String fileName) {
        try {
            System.out.println("**** INGRESO CONTROLADOR DESCARGAR PDF ****");
            String ticketDirectory = "D://ESTUDIOS REALIZADOS//TECNOLOGIA ANALISIS SOFTWARE//tickets";
            Path filePath = Paths.get(ticketDirectory, fileName);
            System.out.println("**** FILEPATH **** " + filePath);
            File file = filePath.toFile();
            System.out.println("**** FILE **** " + file);

            File folder = new File(ticketDirectory);
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {
                for (File files : listOfFiles) {
                    System.out.println("Archivo en directorio: " + file.getName());
                }
            }

            if (!file.exists()) {
                System.out.println("Archivo no encontrado: " + fileName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Resource resource = new UrlResource(file.toURI());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo", e);
        }
    }


}
