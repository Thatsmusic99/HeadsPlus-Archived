package io.github.thatsmusic99.headsplus.config.levels;

import io.github.thatsmusic99.headsplus.api.Level;

public class LowerLevels {

    public class Coal implements Level {

        @Override
        public String getConfigName() {
            return "coal";
        }

        @Override
        public String getDisplayName() {
            return "&8&lCoal";
        }

        @Override
        public int getRequiredXP() {
            return 1500;
        }

        @Override
        public double getAddedVersion() {
            return 0.0;
        }
    }

    public class CoalII implements Level {

        @Override
        public String getConfigName() {
            return "coal_2";
        }

        @Override
        public String getDisplayName() {
            return "&8&lCoal &0&lII";
        }

        @Override
        public int getRequiredXP() {
            return 2000;
        }

        @Override
        public double getAddedVersion() {
            return 0.0;
        }
    }

    public class Iron implements Level {

        @Override
        public String getConfigName() {
            return "iron";
        }

        @Override
        public String getDisplayName() {
            return "&lIron";
        }

        @Override
        public int getRequiredXP() {
            return 2750;
        }

        @Override
        public double getAddedVersion() {
            return 0.0;
        }
    }

    public class IronII implements Level {

        @Override
        public String getConfigName() {
            return "iron_2";
        }

        @Override
        public String getDisplayName() {
            return "&lIron &7&lII";
        }

        @Override
        public int getRequiredXP() {
            return 3500;
        }

        @Override
        public double getAddedVersion() {
            return 0.0;
        }
    }

    public class Redstone implements Level {

        @Override
        public String getConfigName() {
            return "redstone";
        }

        @Override
        public String getDisplayName() {
            return "&c&lRedstone";
        }

        @Override
        public int getRequiredXP() {
            return 4500;
        }

        @Override
        public double getAddedVersion() {
            return 0.0;
        }
    }

    public class RedstoneII implements Level {

        @Override
        public String getConfigName() {
            return "redstone_2";
        }

        @Override
        public String getDisplayName() {
            return "&l&cRedstone &4&lII";
        }

        @Override
        public int getRequiredXP() {
            return 5500;
        }

        @Override
        public double getAddedVersion() {
            return 0.0;
        }
    }
}
