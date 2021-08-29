package com.codinghelmet.moreoojava;

import java.util.List;

public interface PaintingScheduler {
    WorkStream schedule(List<Painter> painters, double sqMeters);
}
