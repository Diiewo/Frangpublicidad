package com.example.demo.Service;

import com.example.demo.Common.JasperReportManager;
import com.example.demo.Enums.TipoReporteEnum;
import com.example.demo.entity.ReporteCronogramaDTO;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@Service
public class ReporteCronogramaService {
    @Autowired
    private JasperReportManager reportManager;

    @Autowired
    private DataSource dataSource;

    public ReporteCronogramaDTO obtenerCronograma(Map<String, Object> params)
            throws JRException, IOException, SQLException {
        String fileName = "ReporteCronograma";
        ReporteCronogramaDTO dto = new ReporteCronogramaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params,
                dataSource.getConnection());

        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLenght(bs.length);

        return dto;

    }
}
