package com.mikaela.hhtimer.appBlock;

import android.annotation.TargetApi;
import android.app.usage.UsageStatsManager;
import android.app.usage.UsageStats;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.app.ListActivity;


import com.mikaela.hhtimer.R;
import com.mikaela.hhtimer.adapter.AppListAdapter;
import com.mikaela.hhtimer.util.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AppList extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        List<PackageInfo> appList;
        appList = getPackageManager().getInstalledPackages(0);
        List<PackageInfo> installedList = new ArrayList<>();

        for (PackageInfo packageInfo : appList) {
            if (/*!isSystemPackage(packageInfo)&&*/ !getApplicationInfo().packageName.equals(packageInfo.packageName)) {
                installedList.add(packageInfo);
                System.out.println("Add installed package: "+ packageInfo.packageName);
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            installedList = sortList(installedList);

        }
        ListAdapter blockListAdapter = new AppListAdapter(this, installedList, BlockUtils.getBlockList(getApplicationContext()));
        ListView blockListView = (ListView) findViewById(android.R.id.list);
        setListAdapter(blockListAdapter);


            /*
            blockListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String food = String.valueOf(parent.getItemAtPosition(position));
                            Toast.makeText(List.this, food, Toast.LENGTH_LONG).show();
                        }
                    }
            );*/

    }
    private boolean isSystemPackage(PackageInfo packageInfo) {
        return (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    public Long getTime (String name, List<AppStatistics> list){
        try {
            for (int i = 0; i < list.size(); i++) {
                if (name.equals(list.get(i).getAppName()))
                    return list.get(i).getAppTime();
            }
        }
        catch (Exception e){
            System.err.println("Caught Exception: " + e.getMessage());

        }
        return 0L;

    }
    @TargetApi(22)
    public List<AppStatistics> getStatistics(){
        List<AppStatistics> appStatistics = new ArrayList<>();
        UsageStatsManager mUsageStatsManager = (UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();
        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.MONTH, -1);
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY, beginCal.getTimeInMillis(), time);

        for (UsageStats usagestats:stats) {
            AppStatistics appStats = new AppStatistics(usagestats.getPackageName(), usagestats.getTotalTimeInForeground());
            try {
                appStatistics.add(appStats);
            }
            catch (Exception e){
                System.err.println("Caught Exception: " + e.getMessage());
            }
        }

        return appStatistics;
    }

    public List sortList(List<PackageInfo> list){
        List<PackageInfo> sortedList = new ArrayList<>();
        List<AppStatistics> appStatistics = getStatistics();



        for (PackageInfo packageInfo:list) {

            int j = 0;
            if (sortedList.isEmpty()) {

                try {
                    sortedList.add(packageInfo);
                }
                catch (Exception e){
                    System.err.println("Caught Exception: " + e.getMessage());
                }
            }

            else {
                for (int i = 0; i < sortedList.size(); i++) {
                    System.out.println("Package name: "+ packageInfo.packageName + ", Package time: "+getTime(packageInfo.packageName, appStatistics));
                    System.out.println("Compared from sorted list item name: "+ sortedList.get(i).packageName + ", Time: "+getTime(sortedList.get(i).packageName, appStatistics));
                    if (getTime(packageInfo.packageName, appStatistics) == 0){
                        sortedList.add(packageInfo);
                        System.out.println("Added"+packageInfo.packageName+ "cause time = 0");
                        break;
                    }

                    else if (getTime(packageInfo.packageName, appStatistics) > (getTime(sortedList.get(i).packageName, appStatistics))) {
                        j = i;
                        sortedList.add(j, packageInfo);
                        System.out.println("Adding cause used: " + packageInfo.packageName+", on place: "+j);
                        break;
                    }

                }

            }
            if(!(sortedList.contains(packageInfo)))
                sortedList.add(packageInfo);
        }

        return sortedList;
    }


}
