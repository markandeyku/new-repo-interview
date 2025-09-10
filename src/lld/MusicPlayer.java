package lld;

import java.util.*;

// 🎵 Enum for music genre
enum Genre {
    POP, ROCK, JAZZ, CLASSICAL, HIPHOP, EDM, COUNTRY, OTHER
}

// 🎵 Enum for PlayMode (Strategy)
enum PlayMode {
    NORMAL, REPEAT, SHUFFLE
}

// 🎵 Song class
class Song {
    private String title;
    private String artist;
    private String format; // mp3, wav
    private Genre genre;
    private int playCount = 0; // track most played

    public Song(String title, String artist, String format, Genre genre) {
        this.title = title;
        this.artist = artist;
        this.format = format;
        this.genre = genre;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getFormat() { return format; }
    public Genre getGenre() { return genre; }
    public int getPlayCount() { return playCount; }
    public void incrementPlayCount() { playCount++; }

    @Override
    public String toString() {
        return title + " by " + artist + " [" + format + ", " + genre + ", Plays: " + playCount + "]";
    }
}

// 🎵 Playlist class
class Playlist {
    private String name;
    private List<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) { songs.add(song); }
    public void removeSong(Song song) { songs.remove(song); }
    public List<Song> getSongs() { return songs; }

    public void displaySongs() {
        System.out.println("Playlist: " + name);
        for (Song song : songs) {
            System.out.println("   " + song);
        }
    }

    public String getName() { return name; }
}

// 🎛 MusicPlayer class
class MusicPlayer {
    private Map<String, Playlist> playlists;
    private Set<Song> favoriteSongs;
    private PlayMode playMode;
    private double speed = 1.0;
    private Queue<Song> queue;
    private Song currentSong;

    public MusicPlayer() {
        playlists = new HashMap<>();
        favoriteSongs = new HashSet<>();
        playMode = PlayMode.NORMAL;
        queue = new LinkedList<>();
    }

    // 🎵 Playlist management
    public void addPlaylist(Playlist playlist) {
        playlists.put(playlist.getName(), playlist);
    }

    public void removePlaylist(String name) {
        playlists.remove(name);
    }

    public void addSongToQueue(Song song) {
        queue.add(song);
    }

    // 🎛 Controls
    public void play() {
        if (currentSong == null) {
            currentSong = queue.poll();
        }
        if (currentSong != null) {
            currentSong.incrementPlayCount();
            System.out.println("▶ Playing: " + currentSong + " at speed x" + speed);
        } else {
            System.out.println("No song in queue.");
        }
    }

    public void pause() {
        if (currentSong != null) {
            System.out.println("⏸ Paused: " + currentSong);
        }
    }

    public void next() {
        switch (playMode) {
            case NORMAL:
                currentSong = queue.poll();
                break;
            case REPEAT:
                // keep same song
                break;
            case SHUFFLE:
                List<Song> shuffled = new ArrayList<>(queue);
                Collections.shuffle(shuffled);
                queue = new LinkedList<>(shuffled);
                currentSong = queue.poll();
                break;
        }
        play();
    }

    public void increaseSpeed() {
        speed += 0.25;
        System.out.println("Playback speed increased: x" + speed);
    }

    public void decreaseSpeed() {
        speed = Math.max(0.5, speed - 0.25);
        System.out.println("Playback speed decreased: x" + speed);
    }

    // ⭐ Favorites
    public void addToFavorites(Song song) {
        favoriteSongs.add(song);
        System.out.println(song.getTitle() + " added to Favorites");
    }

    public void showFavorites() {
        System.out.println("⭐ Favorite Songs:");
        for (Song s : favoriteSongs) {
            System.out.println("   " + s);
        }
    }

    // 🔍 Search (boost by most played)
    public List<Song> search(String keyword) {
        List<Song> results = new ArrayList<>();
        for (Playlist p : playlists.values()) {
            for (Song s : p.getSongs()) {
                if (s.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        s.getArtist().toLowerCase().contains(keyword.toLowerCase())) {
                    results.add(s);
                }
            }
        }
        // sort by playCount (popularity)
        results.sort((a, b) -> b.getPlayCount() - a.getPlayCount());
        return results;
    }

    // 🎛 Change play mode
    public void setPlayMode(PlayMode mode) {
        this.playMode = mode;
        System.out.println("Play mode set to " + mode);
    }
}

// 🎵 Main driver
 class MusicMain {
    public static void main(String[] args) {
        // Songs
        Song s1 = new Song("Shape of You", "Ed Sheeran", "mp3", Genre.POP);
        Song s2 = new Song("Believer", "Imagine Dragons", "wav", Genre.ROCK);
        Song s3 = new Song("Take Five", "Dave Brubeck", "aac", Genre.JAZZ);
        Song s4 = new Song("Fur Elise", "Beethoven", "mp3", Genre.CLASSICAL);

        // Playlists
        Playlist hits = new Playlist("Hits");
        hits.addSong(s1);
        hits.addSong(s2);

        Playlist chill = new Playlist("Chill");
        chill.addSong(s3);
        chill.addSong(s4);

        // Player
        MusicPlayer player = new MusicPlayer();
        player.addPlaylist(hits);
        player.addPlaylist(chill);

        // Queue songs
        player.addSongToQueue(s1);
        player.addSongToQueue(s2);

        // Play
        player.play();
        player.pause();
        player.increaseSpeed();
        player.next(); // play next

        // Favorites
        player.addToFavorites(s1);
        player.showFavorites();

        // Search
        System.out.println("\n🔍 Search for 'bel':");
        for (Song s : player.search("bel")) {
            System.out.println("   " + s);
        }

        // Play mode change
        player.setPlayMode(PlayMode.SHUFFLE);
    }
}
