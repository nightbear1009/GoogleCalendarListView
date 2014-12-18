GoogleCalendarListView
======================

這是一個示範如何寫出類似 googleCalendarListview的範例程式

![alt tag](http://fat.gfycat.com/GentleFewAcornweevil.gif)


Sample
--------

addItemDecoration in RecyclerView
```Java
mRecyclerView.addItemDecoration(new MyItemDecoration());
```
where MyItemDecoration is as below
in getShiftListener return ShiftImageView
```Java
public class MyItemDecoration extends ShiftItemDecoration {
    @Override
    public onShiftListener getShiftListener(View view) {
        return (onShiftListener)view.findViewById(R.id.img);
    }
}
```
use ShiftImageView in adapter's viewHolder

enjoy!!
