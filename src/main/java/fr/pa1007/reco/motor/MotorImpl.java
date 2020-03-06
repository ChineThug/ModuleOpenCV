package fr.pa1007.reco.motor;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.impl.PinImpl;
import fr.pa1007.trobotframework.move.Motor;

public class MotorImpl extends Motor {

    public MotorImpl(PinImpl pwmPin, PinImpl pin) {
        super(pwmPin, pin);
        gpio = GpioFactory.getInstance();
        provider = MotorsImpl.getProvider();
        pwm = 1;
        this.output = gpio.provisionPwmOutputPin(provider, pwmPin, "Pulse 04");
        provider = MotorsImpl.getProvider();
        init();
    }

    @Override
    public void changeSpeed(int speed) {
        if (speed > MotorsImpl.MAX_VALUE) {
            speed = MotorsImpl.MAX_VALUE;
        }
        else if (speed < 0) {
            speed = 0;
        }
        provider.setPwm(pwmPin, speed);
        pwm = speed;
    }

    @Override
    public void forward() {
        forward(pwm);
    }

    @Override
    public void reverse() {
        reverse(pwm);
    }

    @Override
    public void forward(int speed) {
        changeSpeed(speed);
        forwardOutput.setState(PinState.HIGH);
    }

    @Override
    public void reverse(int speed) {
        changeSpeed(speed);
        forwardOutput.setState(PinState.LOW);
    }


    @Override
    public void addSpeed(int dSpeed) {
        changeSpeed(pwm + dSpeed);
    }

    @Override
    public void stop() {
        provider.setAlwaysOff(pwmPin);
    }

    @Override
    public void forwardWithoutChange(int speed) {
        provider.setPwm(pwmPin, speed);
        forwardOutput.setState(PinState.HIGH);
    }

    @Override
    public void reverseWithoutChange(int speed) {
        provider.setPwm(pwmPin, speed);
        forwardOutput.setState(PinState.LOW);
    }

    @Override
    public void unBound() {
        stop();
        gpio.unprovisionPin(forwardOutput);
    }

    @Override
    protected void init() {
        forwardOutput = gpio.provisionDigitalOutputPin(pin, "Forward", PinState.LOW);
    }
}
