package com.kodilla.walletmanager.tools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ToolsManagerTest {

    @Test
    public void positiveTenthRoundDouble() {
        //When
        double negative = ToolsManager.positiveTenthRoundDouble(-20);
        double correct = ToolsManager.positiveTenthRoundDouble(25.25);
        double thousandValue = ToolsManager.positiveTenthRoundDouble(0.0049);
        double round = ToolsManager.positiveTenthRoundDouble(1.999);

        //Then
        assertEquals(0,negative,0);
        assertEquals(25.25,correct,0);
        assertEquals(0,thousandValue,0);
        assertEquals(2,round,0);
    }
}