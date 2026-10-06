/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author romel
 */

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class MutableClock extends Clock{
    private Instant now;
    
    public MutableClock(LocalDateTime start) {
        this.now = start.toInstant(ZoneOffset.UTC);
    }
    
    public void advance(Duration duration) {
        now = now.plus(duration);
    }
    
    @Override
    public ZoneId getZone() {
        return ZoneOffset.UTC;
    }
    
    @Override
    public Clock withZone(ZoneId zone) {
        return this;
    }
    
    @Override
    public Instant instant() {
        return now;
    }
}
