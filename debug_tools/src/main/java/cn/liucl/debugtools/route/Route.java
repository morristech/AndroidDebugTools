package cn.liucl.debugtools.route;

import cn.liucl.debugtools.server.Result;

/**
 * Created by spawn on 17-9-28.
 */

public interface Route {

    /**
     * 路由处理
     *
     * @return 结果
     */
    Result process();
}
