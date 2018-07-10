package de.htwBerlin.ai.kbe.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "songlist")
@Entity
@Table(name = "Songlist")
public class Songlist{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String listName;
    private boolean ispublic;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy="songlist", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
    private Set<Song> songSet;

    public Songlist() {}
    
    public Songlist(String listName, boolean ispublic) {
        this(listName, ispublic, null);
    }
    
    public Songlist(String listName, boolean ispublic, User user) {
    	this.listName = listName;
    	this.ispublic = ispublic;
		this.user = user;
    }
    
    public int getId() {
    	return id;
    }
    
    public void setListName(String name) {
    	this.listName = name;
    }
    
    public String getListName() {
    	return listName;
    }
    
    public boolean isIspublic() {
		return ispublic;
	}

	public void setIspublic(boolean ispublic) {
		this.ispublic = ispublic;
	}
    
    public void setUser(User user) {
        this.user = user; 
    }
  
    @Override
    public String toString() {
        String songs = "Song:\n";
        for(Song s:songSet) {
            songs = songs.concat(s.toString()) + "\n";
        }
        return "Songlist [id=" + id + ", SonglistName= " + listName + " songSet=" + songs + "]";
    }
    
    public Set<Song> getSongSet() {
        if(songSet == null) {
            songSet = new HashSet<>();
        }
        return songSet;
    }

    public void setSongSet(Set<Song> songSet) {
        this.songSet = songSet;
        // Works for JSON, but not for XML
        if(songSet != null) {
            this.songSet.forEach(a-> a.setSongList(this));
        }
    }
    
    public void addSong(Song song) {
        if(songSet == null) {
            songSet = new HashSet<>();
        }
        song.setSongList(this);
        this.songSet.add(song);
    }

	
    
}
