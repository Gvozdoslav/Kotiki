package ru.itmo.kotiki.data.constant;

public class RabbitConstants {

    public static class CatConstants {

        public static final String GET_CATS = "getCats";
        public static final String GET_CAT_BY_ID = "getCatById";
        public static final String SAVE_CAT = "saveCat";
        public static final String UPDATE_CAT = "updateCat";
        public static final String DELETE_CAT = "deleteCat";
        public static final String GET_CATS_BY_NAME = "getCatsByName";
        public static final String GET_CATS_BY_BREED = "getCatsByBreed";
        public static final String GET_CATS_BY_COLOR = "getCatsByColor";
    }

    public static class OwnerConstants {

        public static final String GET_OWNERS = "getOwners";
        public static final String GET_OWNER_BY_ID = "getOwnerById";
        public static final String GET_OWNERS_BY_NAME = "getOwnersByName";
        public static final String SAVE_OWNER = "saveOwner";
        public static final String UPDATE_OWNER = "updateOwner";
        public static final String DELETE_OWNER = "deleteOwner";
    }
}
