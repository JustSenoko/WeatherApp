package com.example.weatherapp;

public final class MainPresenter {

    //Внутреннее поле, будет хранить единственный экземпляр
    private static MainPresenter instance = null;

    // Поле для синхронизации
    private static final Object syncObj = new Object();

    private String city;
    private boolean showPressure;
    private boolean showWind;
    private boolean showFeelsLike;
    private String tUnit;

    // Конструктор (вызывать извне его нельзя, поэтому он приватный)
    private MainPresenter(){
        city = "Moscow";
        showPressure = true;
        showWind = true;
        showFeelsLike = true;
        tUnit = "C";
    }

    public boolean isShowPressure() {
        return showPressure;
    }

    public boolean isShowWind() {
        return showWind;
    }

    public boolean isShowFeelsLike() {
        return showFeelsLike;
    }

    public String getCity() {
        return city;
    }

    public String gettUnit() {
        return tUnit;
    }

    public void setShowPressure(boolean showPressure) {
        this.showPressure = showPressure;
    }

    public void setShowWind(boolean showWind) {
        this.showWind = showWind;
    }

    public void setShowFeelsLike(boolean showFeelsLike) {
        this.showFeelsLike = showFeelsLike;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void settUnit(String tUnit) {
        this.tUnit = tUnit;
    }

    // Метод, который возвращает экземпляр объекта.
    // Если объекта нет, то создаем его.
    public static MainPresenter getInstance(){
        // Здесь реализована «ленивая» инициализация объекта,
        // то есть, пока объект не нужен, не создаем его.
        synchronized (syncObj) {
            if (instance == null) {
                instance = new MainPresenter();
            }
            return instance;
        }
    }

}
