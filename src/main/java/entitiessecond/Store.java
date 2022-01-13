package entitiessecond;

import java.util.List;

public interface Store {

    List<Advertisement> showAdsLastDay();

    List<Advertisement> withPhoto();

    List<Advertisement> differentMark(String mark);

    Advertisement saveADS(Advertisement ads);
}
