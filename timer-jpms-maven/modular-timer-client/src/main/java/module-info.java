import swt6.modular.beans.TimerProvider;

module swt.modular.clients {
    requires swt.modular.beans;
    uses TimerProvider;
    requires org.slf4j;
}