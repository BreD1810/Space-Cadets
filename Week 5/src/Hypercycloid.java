public class Hypercycloid {

    private float radius;
    private float offset;
    private int loopNumber;

    public void setRadius(float input)
    {
        radius = input;
    }

    public void setOffset(float input)
    {
        offset = input;
    }

    public void setLoopNumber(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public float getOffset()
    {
        return offset;
    }

    public float getRadius()
    {
        return radius;
    }

    public int getLoopNumber() {
        return loopNumber;
    }
}
