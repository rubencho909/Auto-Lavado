package com.sena.lavadero.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sena.lavadero.entities.Agenda;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public static final String TICKET_DIRECTORY = "D://ESTUDIOS REALIZADOS//TECNOLOGIA ANALISIS SOFTWARE//tickets";

    public String generateTicketPdf(Agenda agenda) throws IOException, DocumentException {

        Document document = new Document();
        String fileName = "Ticket_" + agenda.getNumeroTicket() + ".pdf";
        File file = new File(TICKET_DIRECTORY + File.separator + fileName);

        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();
        document.add(new Paragraph("Ticket de Agenda: " + agenda.getNumeroTicket()));
        document.add(new Paragraph("Fecha Agenda: " + agenda.getFechaAgenda()));
        document.add(new Paragraph("Servicio: " + agenda.getServicio().getTitulo()));
        document.add(new Paragraph("Tipo Servicio: " + agenda.getTipoServicio().getDescripcion()));
        document.add(new Paragraph("Vehículo: " + agenda.getVehiculo().getPlacaVehiculo()));

        document.close();

        return fileName;
    }

}
