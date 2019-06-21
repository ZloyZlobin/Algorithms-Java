package EventDrivenSimulation;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

public class CollisionSystem
{
    private static final double HZ = 0.5;

    private MinPQ<Event> pq;
    private double t = 0.0;
    private Particle[] particles;

    public CollisionSystem(Particle[] particles)
    {
        this.particles = particles.clone();
    }

    private void predict(Particle a, double limit)
    {
        if (a == null) return;
        for (int i = 0; i < particles.length; i++)
        {
            double dt = a.timeToHit(particles[i]);
            if (t + dt <= limit)
                pq.insert(new Event(t + dt, a, particles[i]));
        }
        double dtX = a.timeToHitVerticalWall();
        double dtY = a.timeToHitHorizontalWall();
        if (t + dtX <= limit) pq.insert(new Event(t + dtX, a, null));
        if (t + dtY <= limit) pq.insert(new Event(t + dtY, null, a));
    }

    private void redraw(double limit)
    {
        StdDraw.clear();
        for (int i = 0; i < particles.length; i++)
        {
            particles[i].draw();
        }
        StdDraw.show();
        StdDraw.pause(20);
        if (t < limit)
        {
            pq.insert(new Event(t + 1.0 / HZ, null, null));
        }
    }

    public void simulate(double limit)
    {
        pq = new MinPQ<>();
        for (int i = 0; i < particles.length; i++)
            predict(particles[i], limit);
        pq.insert(new Event(0, null, null));

        while (!pq.isEmpty())
        {
            Event event = pq.delMin();
            if (!event.isValid()) continue;
            Particle a = event.a;
            Particle b = event.b;

            for (int i = 0; i < particles.length; i++)
                particles[i].move(event.time - t);
            t = event.time;

            if (a != null && b !=  null) a.bounceOff(b);
            else if (a != null && b == null) a.bounceOfVerticalWall();
            else if (a == null && b != null) b.bounceOfHorizontalWall();
            else redraw(limit);

            predict(a, limit);
            predict(b, limit);
        }
    }

    public static void main(String[] args) {
        StdDraw.setCanvasSize(600, 600);
        StdDraw.enableDoubleBuffering();

        Particle[] particles;
        int n = Integer.parseInt(args[0]);
        particles = new Particle[n];
        for (int i = 0; i < n; i++) {
            particles[i] = new Particle();
        }

        CollisionSystem syste = new CollisionSystem(particles);
        syste.simulate(10000);
    }
}
