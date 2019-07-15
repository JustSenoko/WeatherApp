package com.example.weatherapp;

public final class MainPresenter {

    //Внутреннее поле, будет хранить единственный экземпляр
    private static MainPresenter instance = null;

    // Поле для синхронизации
    private static final Object syncObj = new Object();

    private boolean showPressure;
    private boolean showWind;
    private boolean showFeelsLike;

    // Конструктор (вызывать извне его нельзя, поэтому он приватный)
    private MainPresenter(){
        showPressure = true;
        showWind = true;
        showFeelsLike = true;
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

    public void setShowPressure(boolean showPressure) {
        this.showPressure = showPressure;
    }

    public void setShowWind(boolean showWind) {
        this.showWind = showWind;
    }

    public void setShowFeelsLike(boolean showFeelsLike) {
        this.showFeelsLike = showFeelsLike;
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
