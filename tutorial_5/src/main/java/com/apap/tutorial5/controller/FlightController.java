package com.apap.tutorial5.controller;

import java.util.ArrayList;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = new PilotModel();
		pilot.setLicenseNumber(licenseNumber);
		ArrayList<FlightModel> list = new ArrayList<>();
		list.add(new FlightModel());
		pilot.setPilotFlight(list);
		
		model.addAttribute("pilot", pilot);
		model.addAttribute("title", "Add Flight");
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute FlightModel flight, Model model) {
		flightService.addFlight(flight);
		model.addAttribute("title", "Add");
		return "add";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"save"})
	private String addFlightSubmit(@ModelAttribute PilotModel pilot, Model model) {
		System.out.println(pilot.getLicenseNumber());
		PilotModel pilotTemp  = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
		for(FlightModel flight : pilot.getPilotFlight()) {
			flight.setPilot(pilotTemp);
			flightService.addFlight(flight);
		}
		model.addAttribute("title", "Add");
		return "add";
	}
	
	@RequestMapping(value = "/flight/update", method = RequestMethod.GET)
	private String updateFlight(@RequestParam(value = "id") long id, Model model) {
		FlightModel flight = flightService.getFlightById(id);
		model.addAttribute("flight", flight);
		model.addAttribute("title", "Update Flight");
		return "update-flight";
	}
	
	@RequestMapping(value = "/flight/update", method = RequestMethod.POST)
	private String updateFlightSubmit(@ModelAttribute FlightModel flight, Model model) {
		flightService.addFlight(flight);
		model.addAttribute("title", "Update");
		return "update";
	}
	
	@RequestMapping(value = "/flight/delete", method = RequestMethod.GET)
	private String deleteFlight(@RequestParam(value = "id") long id, Model model) {
		flightService.deleteFlightById(id);
		model.addAttribute("title", "Delete");
		return "delete";
	}
	
	@RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
	private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlightById(flight.getId());
		}
		model.addAttribute("title", "Delete");
		return "delete";
	}
	
	@RequestMapping(value = "/flight/view", method = RequestMethod.GET)
	private String viewFlight(@RequestParam(value = "flightNumber") String flightNumber, Model model) {
		FlightModel flight = flightService.getFlightByFlightNumber(flightNumber);
		model.addAttribute("flight", flight);
		model.addAttribute("title", "View Flight");
		return "view-flight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST, params= {"addRow"})
	public String addRow(@ModelAttribute PilotModel pilot, BindingResult bindingResult, Model model) {
		if (pilot.getPilotFlight() == null) {
            pilot.setPilotFlight(new ArrayList<FlightModel>());
        }
		pilot.getPilotFlight().add(new FlightModel());
		
		model.addAttribute("pilot", pilot);
		model.addAttribute("title", "Add Flight");
		return "addFlight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"removeRow"})
	public String removeRow(@ModelAttribute PilotModel pilot, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
	    final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
	    pilot.getPilotFlight().remove(rowId.intValue());
	    
	    model.addAttribute("pilot", pilot);
	    model.addAttribute("title", "Add Flight");
	    return "addFlight";
	}
}
	