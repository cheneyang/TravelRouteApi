package api.app.travelroute.service;

import api.app.travelroute.entity.RouteEntity;
import api.app.travelroute.repository.RouteRepository;
import api.base.common.Util;
import api.base.exception.InvalidParamsException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cc on 2017/4/17.
 */
@Service
public class RouteService {

    @Autowired
    RouteRepository routeRepo;

    public List<RouteEntity> getRouteListByDestId(long destId) {

        return routeRepo.findByDestId(destId);
    }

    public RouteEntity getRoute(long routeId) {

        return routeRepo.findOne(routeId);
    }

    public RouteEntity saveRoute(long destId, long routeId, String name, Double price, String intro, String info, String notice, String img) {
        if (routeId == 0) {
            return createRoute(destId, name, price, intro, info, notice, img);
        }

        RouteEntity route = routeRepo.findOne(routeId);
        return route == null ? createRoute(destId, name, price, intro, info, notice, img) : updateRoute(route, name, price, intro, info, notice, img);
    }

    private RouteEntity createRoute(long destId, String name, Double price, String intro, String info, String notice, String img) {
        if (destId == 0 || StringUtils.isBlank(name) || price == null || StringUtils.isBlank(intro) || StringUtils.isBlank(info) || StringUtils.isBlank(notice) || StringUtils.isBlank(img)) {
            throw new InvalidParamsException();
        }
        RouteEntity route = new RouteEntity();
        route.setDestId(destId);
        route.setName(name);
        route.setPrice(price);
        route.setIntro(intro);
        route.setInfo(info);
        route.setNotice(notice);
        route.setImg(img);
        route.setCreateTime(Util.time());
        route.setUpdateTime(route.getCreateTime());

        return routeRepo.save(route);
    }

    private RouteEntity updateRoute(RouteEntity route, String name, Double price, String intro, String info, String notice, String img) {
        if (name != null) {
            route.setName(name);
        }
        if (price != null) {
            route.setPrice(price);
        }
        if (intro != null) {
            route.setIntro(intro);
        }
        if (info != null) {
            route.setInfo(info);
        }
        if (notice != null) {
            route.setNotice(notice);
        }
        if (img != null) {
            route.setImg(img);
        }
        route.setUpdateTime(Util.time());

        return routeRepo.save(route);
    }

}