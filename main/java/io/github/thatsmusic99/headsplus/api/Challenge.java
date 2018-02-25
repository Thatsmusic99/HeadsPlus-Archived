package io.github.thatsmusic99.headsplus.api;

import io.github.thatsmusic99.headsplus.config.challenges.HPChallengeRewardTypes;
import io.github.thatsmusic99.headsplus.config.challenges.HeadsPlusChallengeTypes;

import java.util.List;

public class Challenge {

    private String configName;
    private String mainName;
    private String header;
    private List<String> description;
    private int requiredHeadAmount;
    private HeadsPlusChallengeTypes challengeType;
    private HPChallengeRewardTypes rewardType;
    private Object rewardValue;
    private int rewardItemAmount;
}
