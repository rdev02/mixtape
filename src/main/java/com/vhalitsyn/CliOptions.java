package com.vhalitsyn;

import java.io.File;

/**
 * Command line args POJO
 */
public class CliOptions {
    File inputFile;
    File editsFile;
    File outputFile;

    public File getInputFile()
    {
        return inputFile;
    }

    public void setInputFile(File inputFile)
    {
        this.inputFile = inputFile;
    }

    public File getEditsFile()
    {
        return editsFile;
    }

    public void setEditsFile(File editsFile)
    {
        this.editsFile = editsFile;
    }

    public File getOutputFile()
    {
        return outputFile;
    }

    public void setOutputFile(File outputFile)
    {
        this.outputFile = outputFile;
    }
}
