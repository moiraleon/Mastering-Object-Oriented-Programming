package com.codinghelmet.moreoojava;

import com.codinghelmet.moreoojava.common.ForwardingStream;

import java.time.Duration;
import java.util.Optional;
import java.util.stream.Stream;

public class DurationsStream implements ForwardingStream<Duration> {
    private Stream<Duration> stream;

    public DurationsStream(Stream<Duration> durations) {
        this.stream = durations;
    }

    @Override
    public Stream<Duration> getStream() { return this.stream; }

    public Duration maxOfMany() {
        return this.stream.max(Duration::compareTo).get();
    }

    public Optional<Duration> min() {
        return this.stream.min(Duration::compareTo);
    }
}
