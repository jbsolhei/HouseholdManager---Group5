package test;

import javax.inject.Inject;
import java.math.BigDecimal;

public class AutomatedTellerMachineImpl implements AutomatedTellerMachine {
    private ATMTransport transport;

    public void deposit(BigDecimal bd) {
        System.out.println("deposit called");
        transport.communicateWithBank(new byte[]{});
    }
    public void withdraw(BigDecimal bd) {
        System.out.println("withdraw called");
        transport.communicateWithBank(new byte[]{});
    }

    @Inject
    public void setTransport(ATMTransport transport) {
        this.transport = transport;
    }
}


