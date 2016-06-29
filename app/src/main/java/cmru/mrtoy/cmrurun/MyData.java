package cmru.mrtoy.cmrurun;

/**
 * Created by MrTOYPT on 28/6/2559.
 */
public class MyData {
    // Explicit
    private int[] avataInts = new int[]{20,R.drawable.bird48 , R.drawable.doremon48 ,
    R.drawable.kon48 ,R.drawable.nobita48 ,R.drawable.rat48};
    private double[] latStationDoubles = new double[]{18.808869, 18.808788, 18.805885, 18.806241};
    private double[] lngStationDoubles = new double[]{99.017871, 99.018768, 99.018228, 99.017471};
    private int[] iconStationInts = new int[]{R.drawable.build1, R.drawable.build2,
            R.drawable.build3, R.drawable.build4};



    // สร้าง Geter โดยการกด Alt + Ins เลือก Getter แล้ว OK

    public int[] getAvataInts() {
        return avataInts;
    }


    public double[] getLatStationDoubles() {
        return latStationDoubles;
    }

    public double[] getLngStationDoubles() {
        return lngStationDoubles;
    }

    public int[] getIconStationInts() {
        return iconStationInts;
    }
} // end of MyData Class
