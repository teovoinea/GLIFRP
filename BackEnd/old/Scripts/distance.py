def pos_distance(lon1, lat1, lon2,lat2):
    dlon = lon1 - lon2
    dlat = lat1 - lat2
    return sqrt((dlon**2) + (dlat**2))
