/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfkids.dto;

import java.util.List;

/**
 *
 * @author Aubrey Malabie
 */
public class ResponseDTO {

    private int statusCode, totalPages, totalClubs;
    private String message, gcmRegistrationID;
    private LeaderBoardDTO leaderBoard;
    private PersonalPlayerDTO personalPlayer;
    private List<LeaderBoardCarrierDTO> leaderBoardCarriers;
    private List<PersonalPlayerDTO> personalPlayerList;
    private List<PersonalScoreDTO> personalScoreList;
    private List<GolfGroupDTO> golfGroups;
    private List<ParentDTO> parents;
    private List<PlayerDTO> players;
    private List<LeaderBoardDTO> tourneyPlayers;
    private List<TourneyScoreByRoundDTO> tourneyScoreByRoundList;
    private List<TournamentDTO> tournaments;
    private List<LeaderBoardDTO> leaderBoardList;
    private List<LeaderBoardTeamDTO> leaderBoardTeamList;
    private List<CountryDTO> countries;
    private List<ProvinceDTO> provinces;
    private List<ClubDTO> clubs;
    private List<AgeGroupDTO> ageGroups;
    private List<AdministratorDTO> administrators;
    private List<GolfGroupPlayerDTO> golfGroupPlayers;
    private List<GolfGroupParentDTO> golfGroupParents;
    private List<ScorerDTO> scorers;
    private GolfGroupDTO golfGroup;
    private AppUserDTO appUser;
    private List<String> imageFileNames;
    private AdministratorDTO administrator;
    private List<VideoClipDTO> videoClips;
    private List<ErrorStoreAndroidDTO> errorStoreAndroidList;
    private List<ErrorStoreDTO> errorStoreList;
    private String log;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
    
    //
    public static final int LOGIN_EXCEPTION = 101;
    public static final int DATA_EXCEPTION = 102;
    public static final int DUPLICATE_EXCEPTION = 103;
    public static final int GENERIC_EXCEPTION = 109;

    public List<LeaderBoardCarrierDTO> getLeaderBoardCarriers() {
        return leaderBoardCarriers;
    }

    public void setLeaderBoardCarriers(List<LeaderBoardCarrierDTO> leaderBoardCarriers) {
        this.leaderBoardCarriers = leaderBoardCarriers;
    }

    public List<ErrorStoreDTO> getErrorStoreList() {
        return errorStoreList;
    }

    public void setErrorStoreList(List<ErrorStoreDTO> errorStoreList) {
        this.errorStoreList = errorStoreList;
    }

    public AppUserDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserDTO appUser) {
        this.appUser = appUser;
    }
    

    public int getTotalPages() {
        return totalPages;
    }

    public String getGcmRegistrationID() {
        return gcmRegistrationID;
    }

    public void setGcmRegistrationID(String gcmRegistrationID) {
        this.gcmRegistrationID = gcmRegistrationID;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalClubs() {
        return totalClubs;
    }

    public void setTotalClubs(int totalClubs) {
        this.totalClubs = totalClubs;
    }

    
  
    public int getStatusCode() {
        return statusCode;
    }

    public List<LeaderBoardTeamDTO> getLeaderBoardTeamList() {
        return leaderBoardTeamList;
    }

    public void setLeaderBoardTeamList(List<LeaderBoardTeamDTO> leaderBoardTeamList) {
        this.leaderBoardTeamList = leaderBoardTeamList;
    }

    public LeaderBoardDTO getLeaderBoard() {
        return leaderBoard;
    }

    public void setLeaderBoard(LeaderBoardDTO leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    public List<LeaderBoardDTO> getTourneyPlayers() {
        return tourneyPlayers;
    }

    public void setTourneyPlayers(List<LeaderBoardDTO> tourneyPlayers) {
        this.tourneyPlayers = tourneyPlayers;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public AdministratorDTO getAdministrator() {
        return administrator;
    }

    public PersonalPlayerDTO getPersonalPlayer() {
        return personalPlayer;
    }

    public void setPersonalPlayer(PersonalPlayerDTO personalPlayer) {
        this.personalPlayer = personalPlayer;
    }

    public List<PersonalPlayerDTO> getPersonalPlayerList() {
        return personalPlayerList;
    }

    public void setPersonalPlayerList(List<PersonalPlayerDTO> personalPlayerList) {
        this.personalPlayerList = personalPlayerList;
    }

    public List<PersonalScoreDTO> getPersonalScoreList() {
        return personalScoreList;
    }

    public void setPersonalScoreList(List<PersonalScoreDTO> personalScoreList) {
        this.personalScoreList = personalScoreList;
    }

  

    public void setAdministrator(AdministratorDTO administrator) {
        this.administrator = administrator;
    }

    public GolfGroupDTO getGolfGroup() {
        return golfGroup;
    }

    public void setGolfGroup(GolfGroupDTO golfGroup) {
        this.golfGroup = golfGroup;
    }

    public List<ErrorStoreAndroidDTO> getErrorStoreAndroidList() {
        return errorStoreAndroidList;
    }

    public void setErrorStoreAndroidList(List<ErrorStoreAndroidDTO> errorStoreAndroidList) {
        this.errorStoreAndroidList = errorStoreAndroidList;
    }

    public List<ScorerDTO> getScorers() {
        return scorers;
    }

    public void setScorers(List<ScorerDTO> scorers) {
        this.scorers = scorers;
    }

    public List<GolfGroupPlayerDTO> getGolfGroupPlayers() {
        return golfGroupPlayers;
    }

    public void setGolfGroupPlayers(List<GolfGroupPlayerDTO> golfGroupPlayers) {
        this.golfGroupPlayers = golfGroupPlayers;
    }

    public List<GolfGroupParentDTO> getGolfGroupParents() {
        return golfGroupParents;
    }

    public void setGolfGroupParents(List<GolfGroupParentDTO> golfGroupParents) {
        this.golfGroupParents = golfGroupParents;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GolfGroupDTO> getGolfGroups() {
        return golfGroups;
    }

    public List<String> getImageFileNames() {
        return imageFileNames;
    }

    public void setImageFileNames(List<String> imageFileNames) {
        this.imageFileNames = imageFileNames;
    }

    public void setGolfGroups(List<GolfGroupDTO> golfGroups) {
        this.golfGroups = golfGroups;
    }

    public List<VideoClipDTO> getVideoClips() {
        return videoClips;
    }

    public void setVideoClips(List<VideoClipDTO> videoClips) {
        this.videoClips = videoClips;
    }

    public List<TourneyScoreByRoundDTO> getTourneyScoreByRoundList() {
        return tourneyScoreByRoundList;
    }

    public void setTourneyScoreByRoundList(List<TourneyScoreByRoundDTO> tourneyScoreByRoundList) {
        this.tourneyScoreByRoundList = tourneyScoreByRoundList;
    }

    public List<ParentDTO> getParents() {
        return parents;
    }

    public void setParents(List<ParentDTO> parents) {
        this.parents = parents;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }

    public List<TournamentDTO> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<TournamentDTO> tournaments) {
        this.tournaments = tournaments;
    }

    public List<LeaderBoardDTO> getLeaderBoardList() {
        return leaderBoardList;
    }

    public void setLeaderBoardList(List<LeaderBoardDTO> leaderBoardList) {
        this.leaderBoardList = leaderBoardList;
    }

    public List<CountryDTO> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryDTO> countries) {
        this.countries = countries;
    }

    public List<ProvinceDTO> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<ProvinceDTO> province) {
        this.provinces = province;
    }

    public List<ClubDTO> getClubs() {
        return clubs;
    }

    public void setClubs(List<ClubDTO> clubs) {
        this.clubs = clubs;
    }

    public List<AgeGroupDTO> getAgeGroups() {
        return ageGroups;
    }

    public void setAgeGroups(List<AgeGroupDTO> ageGroups) {
        this.ageGroups = ageGroups;
    }

    public List<AdministratorDTO> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(List<AdministratorDTO> administrators) {
        this.administrators = administrators;
    }
}
