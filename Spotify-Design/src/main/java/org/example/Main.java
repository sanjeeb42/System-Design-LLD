package org.example;

// SpotifyLikeApp.java
import java.util.*;

// -------------------- Core Domain --------------------
interface User {
    String getUserId();
    String getUsername();
    String getEmail();
    boolean isPremium();
    List<Song> getLikedSongs();
    List<Song> getPlayHistory();
    void addToLikedSongs(Song song);
    void addToPlayHistory(Song song);
    String getUserType();
}

class FreeUser implements User {
    private String userId;
    private String username;
    private String email;
    private List<Song> likedSongs;
    private List<Song> playHistory;

    public FreeUser(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.likedSongs = new ArrayList<>();
        this.playHistory = new ArrayList<>();
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public boolean isPremium() { return false; }
    public List<Song> getLikedSongs() { return likedSongs; }
    public List<Song> getPlayHistory() { return playHistory; }

    public void addToLikedSongs(Song song) {
        // Free users have a limit of 25 liked songs
        if (likedSongs.size() >= 25) {
            System.out.println("[Limit] Free users can only like 25 songs. Upgrade to Premium!");
            return;
        }
        if (!likedSongs.contains(song)) {
            likedSongs.add(song);
        }
    }

    public void addToPlayHistory(Song song) {
        playHistory.add(song);
        // Keep only last 20 songs for free users
        if (playHistory.size() > 20) {
            playHistory.remove(0);
        }
    }

    public String getUserType() { return "Free User"; }

    @Override
    public String toString() {
        return username + " (Free)";
    }
}

class PremiumUser implements User {
    private String userId;
    private String username;
    private String email;
    private List<Song> likedSongs;
    private List<Song> playHistory;

    public PremiumUser(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.likedSongs = new ArrayList<>();
        this.playHistory = new ArrayList<>();
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public boolean isPremium() { return true; }
    public List<Song> getLikedSongs() { return likedSongs; }
    public List<Song> getPlayHistory() { return playHistory; }

    public void addToLikedSongs(Song song) {
        // Premium users have unlimited liked songs
        if (!likedSongs.contains(song)) {
            likedSongs.add(song);
        }
    }

    public void addToPlayHistory(Song song) {
        playHistory.add(song);
        // Keep last 100 songs for premium users
        if (playHistory.size() > 100) {
            playHistory.remove(0);
        }
    }

    public String getUserType() { return "Premium User"; }

    @Override
    public String toString() {
        return username + " (Premium)";
    }
}

class Song {
    private String title;
    private String artist;
    private String album;
    private int duration; // seconds

    public Song(String title, String artist, String album, int duration) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public int getDuration() { return duration; }

    @Override
    public String toString() {
        return "\"" + title + "\" by " + artist + " (" + album + ")";
    }
}

// -------------------- Strategy Pattern --------------------
interface PlaybackStrategy {
    void play(Song song);
    void pause();
}

class OnlinePlayback implements PlaybackStrategy {
    public void play(Song song) {
        System.out.println("[OnlinePlayback] Streaming " + song);
    }
    public void pause() {
        System.out.println("[OnlinePlayback] Paused");
    }
}

class OfflinePlayback implements PlaybackStrategy {
    public void play(Song song) {
        System.out.println("[OfflinePlayback] Playing downloaded " + song);
    }
    public void pause() {
        System.out.println("[OfflinePlayback] Paused");
    }
}

// -------------------- Core Services Architecture --------------------
interface UserActionService {
    void handleUserAction(User user, Song song, String actionType);
    String getServiceName();
}

class BookmarkService implements UserActionService {
    public void addBookmark(User user, Song song) {
        user.addToLikedSongs(song);
        System.out.println("[BookmarkService] " + song.getTitle() + " bookmarked for " + user.getUsername());
    }

    @Override
    public void handleUserAction(User user, Song song, String actionType) {
        if ("BOOKMARK".equals(actionType)) {
            addBookmark(user, song);
        }
    }

    @Override
    public String getServiceName() {
        return "BookmarkService";
    }
}

class ShareService implements UserActionService {
    public String createShareLink(Song song, User user) {
        String shareId = "spotify.ly/sng" + Math.abs(song.getTitle().hashCode()) % 10000;
        System.out.println("[ShareService] Share link created: " + shareId);
        System.out.println("[ShareService] " + user.getUsername() + " is sharing: " + song.getTitle());
        return shareId;
    }

    @Override
    public void handleUserAction(User user, Song song, String actionType) {
        if ("SHARE".equals(actionType)) {
            createShareLink(song, user);
        }
    }

    @Override
    public String getServiceName() {
        return "ShareService";
    }
}

class AnalyticsService implements UserActionService {
    public void trackUserAction(User user, Song song, String action) {
        System.out.println("[Analytics] Tracking: " + user.getUsername() + " " + action + " " + song.getTitle());
        System.out.println("[Analytics] Data sent to recommendation engine");
    }

    @Override
    public void handleUserAction(User user, Song song, String actionType) {
        // Analytics tracks ALL actions
        trackUserAction(user, song, actionType);
    }

    @Override
    public String getServiceName() {
        return "AnalyticsService";
    }
}

// -------------------- Service Manager --------------------
class UserActionServiceManager {
    private List<UserActionService> services = new ArrayList<>();

    public void addService(UserActionService service) {
        services.add(service);
        System.out.println("[ServiceManager] Registered: " + service.getServiceName());
    }

    public void processUserAction(User user, Song song, String actionType) {
        System.out.println("[ServiceManager] Processing " + actionType + " for " + user.getUsername());
        for (UserActionService service : services) {
            service.handleUserAction(user, song, actionType);
        }
    }

    public List<String> getRegisteredServices() {
        return services.stream()
                .map(UserActionService::getServiceName)
                .collect(ArrayList::new, (list, name) -> list.add(name), (list1, list2) -> list1.addAll(list2));
    }
}

// -------------------- Facade --------------------
class MusicAppFacade {
    private PlaybackStrategy playbackStrategy;
    private List<Song> catalog = new ArrayList<>();
    private Song currentSong;
    private User currentUser;
    private UserActionServiceManager serviceManager = new UserActionServiceManager();

    public MusicAppFacade(PlaybackStrategy playbackStrategy) {
        this.playbackStrategy = playbackStrategy;
        // sample catalog
        catalog.add(new Song("Tum Hi Ho", "Arijit Singh", "Aashiqui 2", 250));
        catalog.add(new Song("Shape of You", "Ed Sheeran", "Divide", 240));
        catalog.add(new Song("Believer", "Imagine Dragons", "Evolve", 210));

        // Register default services
        serviceManager.addService(new BookmarkService());
        serviceManager.addService(new ShareService());
        serviceManager.addService(new AnalyticsService());
    }

    // User management
    public void loginUser(User user) {
        this.currentUser = user;
        System.out.println("[Login] Welcome " + user.toString());
    }

    public void logoutUser() {
        if (currentUser != null) {
            System.out.println("[Logout] Goodbye " + currentUser.getUsername());
            currentUser = null;
        }
    }

    // Search - can include user's liked songs in results
    public List<Song> searchContent(String query) {
        System.out.println("[Search] Searching for: " + query);
        List<Song> results = new ArrayList<>();
        for (Song s : catalog) {
            if (s.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    s.getArtist().toLowerCase().contains(query.toLowerCase())) {
                results.add(s);
            }
        }
        System.out.println("[Search] Found " + results.size() + " results");
        return results;
    }

    // Playback with user tracking
    public void playContent(Song song) {
        if (currentUser == null) {
            System.out.println("[Error] Please login first to play music");
            return;
        }

        currentSong = song;
        currentUser.addToPlayHistory(song);
        playbackStrategy.play(song);
        System.out.println("[User Activity] Added to " + currentUser.getUsername() + "'s play history");
    }

    public void pauseContent() {
        playbackStrategy.pause();
    }

    // User-specific actions
    public void shareContent() {
        if (currentSong != null && currentUser != null) {
            System.out.println("[Share] " + currentUser.getUsername() + " is sharing:");
            // Directly use ShareService
            new ShareService().createShareLink(currentSong, currentUser);
        }
    }

    public void bookmarkContent() {
        if (currentSong != null && currentUser != null) {
            currentUser.addToLikedSongs(currentSong);
            System.out.println("[Bookmark] Added to " + currentUser.getUsername() + "'s liked songs");
            // Directly use BookmarkService
            new BookmarkService().addBookmark(currentUser, currentSong);
        }
    }

    // User-specific features
    public void showUserLikedSongs() {
        if (currentUser != null) {
            System.out.println("[Liked Songs] " + currentUser.getUsername() + "'s liked songs:");
            for (Song song : currentUser.getLikedSongs()) {
                System.out.println("  - " + song);
            }
        }
    }

    public void showUserPlayHistory() {
        if (currentUser != null) {
            System.out.println("[Play History] " + currentUser.getUsername() + "'s recent plays:");
            List<Song> history = currentUser.getPlayHistory();
            for (int i = Math.max(0, history.size() - 5); i < history.size(); i++) {
                System.out.println("  - " + history.get(i));
            }
        }
    }
}

// -------------------- Client (User) --------------------
public class Main {
    public static void main(String[] args) {
        // Create users using new interface implementations
        User premiumUser = new PremiumUser("1", "Sanjeeb", "sanjeeb@gmail.com");
        User freeUser = new FreeUser("2", "Ishika", "ishika@gmail.com");

        // Use online playback initially
        MusicAppFacade app = new MusicAppFacade(new OnlinePlayback());

        System.out.println("=== Spotify-like Music App Demo ===\n");

        // Login premium user
        app.loginUser(premiumUser);

        // Happy Flow
        System.out.println("\nUser opens app...");
        List<Song> results = app.searchContent("Arijit");

        if (!results.isEmpty()) {
            Song song = results.get(0);

            System.out.println("\nUser selects a song...");
            app.playContent(song);

            System.out.println("\nUser pauses the song...");
            app.pauseContent();

            System.out.println("\nUser bookmarks the song...");
            app.bookmarkContent();

            System.out.println("\nUser shares the song...");
            app.shareContent();
        }

        // Play another song
        System.out.println("\n--- Playing more songs ---");
        List<Song> shapeResults = app.searchContent("Shape");
        if (!shapeResults.isEmpty()) {
            app.playContent(shapeResults.get(0));
        }

        // Show user activity
        System.out.println("\n--- User Activity Summary ---");
        app.showUserPlayHistory();
        app.showUserLikedSongs();

        // Switch to free user
        System.out.println("\n--- Switching Users ---");
        app.logoutUser();
        app.loginUser(freeUser);

        // Try to play without login (this won't work as expected since we just logged in)
        List<Song> believerResults = app.searchContent("Believer");
        if (!believerResults.isEmpty()) {
            app.playContent(believerResults.get(0));
            app.bookmarkContent();
        }

        app.showUserPlayHistory();
        app.showUserLikedSongs();

        app.logoutUser();
    }
}
