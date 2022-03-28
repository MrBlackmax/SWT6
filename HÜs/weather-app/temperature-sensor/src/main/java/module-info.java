import swt6.schwarz.weather.model.Sensor;

module temperature.sensor {
    requires weather.model;
    requires org.slf4j;
    uses Sensor;
    exports swt6.schwarz.temp;
}