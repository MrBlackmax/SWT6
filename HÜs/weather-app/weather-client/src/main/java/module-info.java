import swt6.schwarz.weather.station.WeatherStation;

module weather.client {
    requires weather.station;
    requires org.slf4j;
    uses WeatherStation;
}