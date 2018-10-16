package cn.liucl.debugtools.route;

import android.content.Context;

import cn.liucl.debugtools.disk.DefaultDiskHelper;
import cn.liucl.debugtools.disk.DiskException;
import cn.liucl.debugtools.disk.DiskHelper;
import cn.liucl.debugtools.server.HttpParamsParser;
import cn.liucl.debugtools.server.Result;

/**
 * Created by spawn on 2018/1/23.
 */

public class DiskRoute implements Route {


    private final Context mContext;

    public DiskRoute(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Result process(HttpParamsParser.Request request) {
        DiskHelper helper = new DefaultDiskHelper(mContext);
        String action = request.getRequestURI().split("/")[2];
        Result result = new Result();
        try {
            result.setMessage("ok");
            result.setSuccessful(true);
            switch (action) {
                case "zipFolderDownload":
                    break;
                case "list":
                default:
                    String path = request.getGetParameter("path");
                    String type = request.getGetParameter("type");
                    String folderList = helper.getFolderList(type, path);
                    result.setObj(folderList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setSuccessful(false);
        }
        return result;
    }
}
