# Android Workshop (28th & 29th December 2019)
## This project consists of 3 Activities

## MainActivity
This activity shows you how to implement a <b>RecyclerView</b> and other <b>Touch Events related with RecyclerView.</b> 

## NewsActivity
This activity shows you how to connect with internet to retrive data from [News API](https://newsapi.org/) using [Volley Library](https://developer.android.com/training/volley/index.html) and display it using a RecyclerView. This also includes <b>Shared Element Transition</b>. News Title and Description are sent to NewsDetailsActivity via <b>Intent<b>.

### Update your own api_keyValue in [NetworkUtils.java](https://github.com/kavinraju/Android-Workshop-2/blob/master/app/src/main/java/skr/app/dev/myapplication/utils/NetworkUtils.java). You can get it from [News API](https://newsapi.org/).

## NewsDetailsActivity
This activity shows details retrived in NewsActivity.

In [<b>AndroidManifest.xml</b>](https://github.com/kavinraju/Android-Workshop-2/blob/master/app/src/main/AndroidManifest.xml) file you need to put the <b>intent-filter</b> tag in MainActivity for viewing the RecyclerView example and put the <b>intent-filter</b> tag in NewsActivity for viewing the News API example, while you're running the project.
