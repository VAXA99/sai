package com;

import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DecisionMakingTreeInitializer {

    public static DecisionMakingTree initializeDecisionMakingTreeFromExcel() throws IOException {
        File excelFile = selectExcelFile();
        if (excelFile == null) {
            // User canceled file selection
            return null;
        }

        FileInputStream fileInputStream = new FileInputStream(excelFile);
        Workbook workbook = WorkbookFactory.create(fileInputStream);


        Sheet initialFinalStatesSheet = workbook.getSheetAt(0);
        Sheet statesDataSheet = workbook.getSheetAt(1);

        List<Link> links = readInitialFinalStates(initialFinalStatesSheet);
        List<Node> nodes = readStatesData(statesDataSheet);

        // Assuming the first node is the root node
        Node rootNode = nodes.get(0);

        return new DecisionMakingTree(rootNode, nodes, links);
    }

    private static List<Node> readStatesData(Sheet sheet) {
        List<Node> nodes = new ArrayList<>();

        for (Row row : sheet) {
            Integer index = (int) row.getCell(0).getNumericCellValue();
            String data = row.getCell(1).getStringCellValue();
            Boolean isEndOfSearch = row.getCell(2).getBooleanCellValue();

            nodes.add(new Node(index, data, isEndOfSearch));
        }

        return nodes;
    }

    private static List<Link> readInitialFinalStates(Sheet sheet) {
        List<Link> links = new ArrayList<>();

        for (Row row : sheet) {
            Integer departNode = (int) row.getCell(0).getNumericCellValue();
            Integer destinNode = (int) row.getCell(1).getNumericCellValue();

            String data = row.getCell(2).getStringCellValue();

            links.add(new Link(departNode, destinNode, data));
        }

        return links;
    }

    private static File selectExcelFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите EXCEL-файл");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xls", "xlsx"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }
}

