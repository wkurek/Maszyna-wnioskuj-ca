package util.parser;

import model.Clausure;

import java.util.List;

public interface Parser {
    List<Clausure> getClausures(String filePath);
}
