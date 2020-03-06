package fr.pa1007.reco;

import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.io.i2c.I2CFactory;
import fr.pa1007.reco.motor.MotorsImpl;
import fr.pa1007.reco.motor.ServoImpl;
import fr.pa1007.trobotframework.event.ApplicationSelectedEvent;
import fr.pa1007.trobotframework.event.ModuleLoadedEvent;
import fr.pa1007.trobotframework.move.LinkedMotors;
import fr.pa1007.trobotframework.move.Motor;
import fr.pa1007.trobotframework.move.Servo;
import fr.pa1007.trobotframework.server.ServerUDP;
import fr.pa1007.trobotframework.utils.Module;

import java.io.IOException;

public class Main extends Module {

    private Servo servo;
    private ServerUDP server;
    private Motor m1;
    private Motor m2;
    private LinkedMotors lM;

    @Override
    public void appSelected(ApplicationSelectedEvent applicationSelectedEvent) {
        new Affichage();
    }

    @Override
    public Class<ModuleLoadedEvent> getEventClass() {
        return ModuleLoadedEvent.class;
    }

    @Override
    public void listener(ModuleLoadedEvent moduleLoadedEvent) {
        try {
            server = ServerUDP.getInstance();
            m1 = MotorsImpl.MOTOR_1;
            m2 = MotorsImpl.MOTOR_2;
            lM = new LinkedMotors(m1, m2);
            servo = new ServoImpl(this);
            servo.getPwm().setPWMFreqency(PCA9685GpioProvider.ANALOG_SERVO_FREQUENCY.floatValue());
        } catch (IOException | I2CFactory.UnsupportedBusNumberException e) {
            logger.error(e);
        }
        new Affichage();
    }
}
