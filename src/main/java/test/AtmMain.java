package test;

import java.math.BigDecimal;

public class AtmMain {
    public static void main (String[] args) {
        AutomatedTellerMachine atm = new AutomatedTellerMachineImpl();
        //ATMTransport transport = new SoapAtmTransport();
        /* Inject the transport. */
        //((AutomatedTellerMachineImpl)atm).setTransport(transport);
        atm.withdraw(new BigDecimal("10.00"));
        atm.deposit(new BigDecimal("100.00"));
    }
}