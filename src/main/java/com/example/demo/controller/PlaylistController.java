package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.PlayList;
import com.example.demo.entities.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;


@Controller
public class PlaylistController {
	@Autowired
	SongService songService;
	
 @GetMapping("/createPlaylist")
 public String createPlaylist(Model model) {
	 //fetch the song list
	 List<Song> songList=songService.fetchAllSongs();
	 model.addAttribute("songs",songList);
	 return "createPlaylist";
 }
 
 @Autowired
 PlaylistService playlistService;
 @PostMapping("/addPlaylist")
 public String addPlaylist(@ModelAttribute PlayList playlist) {
	 playlistService.addPlaylist(playlist);
	 //updating the song table
	 List<Song> songList=playlist.getSongs();
	 for(Song s: songList) {
		s.getPlaylists().add(playlist);//Adding the playlist into the song table
		//update song object in database
		songService.updateSong(s);// calls updateSong method frpm songServiceImplementation
		}
	 return "adminHome";
 }
 
 @GetMapping("/viewPlaylists")
 public String viewPlaylists(Model model) {
	 List<PlayList> allPlaylists=playlistService.fetchAllPlaylists();
	 model.addAttribute("allplaylists",allPlaylists);
	 return "displayPlaylists";
	 
 }
 
 
}
