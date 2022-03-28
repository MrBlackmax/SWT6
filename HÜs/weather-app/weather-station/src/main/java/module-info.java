module weather.station {
    requires temperature.sensor;
    requires transitive weather.model;
    exports swt6.schwarz.weather.station;
    exports swt6.schwarz.weather.factories;
}