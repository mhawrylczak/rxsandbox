package pl.allegro.atm.workshop.rx.mobius.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllegroPaymentsCollection {
    private boolean transfer;
    private boolean payu;
    private boolean onDelivery;
    private boolean vatInvoice;
    private List<String> accountNumbers;

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public boolean isPayu() {
        return payu;
    }

    public void setPayu(boolean payu) {
        this.payu = payu;
    }

    public boolean isOnDelivery() {
        return onDelivery;
    }

    public void setOnDelivery(boolean onDelivery) {
        this.onDelivery = onDelivery;
    }

    public boolean isVatInvoice() {
        return vatInvoice;
    }

    public void setVatInvoice(boolean vatInvoice) {
        this.vatInvoice = vatInvoice;
    }

    public List<String> getAccountNumbers() {
        return accountNumbers;
    }

    public void setAccountNumbers(List<String> accountNumbers) {
        this.accountNumbers = accountNumbers;
    }
}
