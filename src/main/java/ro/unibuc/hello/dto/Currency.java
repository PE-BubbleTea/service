package ro.unibuc.hello.dto;

public class Currency {

    private final String result;
    private final Object rates;

    public Currency() {
        this.result = "";
        this.rates = new Object();
    }

    public Currency(String result, Object rates) {
        this.result = result;
        this.rates = rates;
    }

    public String getResult() {
        return result;
    }

    public Object getRates() {
        return rates;
    }
}
