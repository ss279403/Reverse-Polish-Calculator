package com.lmig.gfc.rpn.controllers;

import java.util.Stack;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lmig.gfc.rpn.models.OneArgumentUndoer;
import com.lmig.gfc.rpn.models.TwoArgumentUndoer;

@Controller
public class RpnController {

	private Stack<Double> stack;
	private OneArgumentUndoer undoer;

	RpnController() {
		stack = new Stack<Double>();
	}

	@GetMapping("/")
	public ModelAndView rpnPage() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("stack", stack);
		mv.addObject("hasTwoOrMoreNumbers", stack.size() >= 2);
		mv.addObject("hasUndoer", undoer != null);
		mv.setViewName("calculator");
		return mv;
	}

	@PostMapping("/enter")
	public ModelAndView enterPage(double value) {
		stack.push(value);

		undoer = null;

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/");
		return mv;
	}

	@PostMapping("/abs")
	public ModelAndView absPage() {

		double value = stack.pop();
		undoer = new OneArgumentUndoer(value);
		double result = Math.abs(value);
//		if (value < 0) {
//			value = -1 * value;
//		}
		stack.push(result);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/");
		return mv;
	}

	@PostMapping("/add")
	public ModelAndView addPage() {

		double first = stack.pop();
		double second = stack.pop();
		double result = first + second;
		stack.push(result);

		undoer = new TwoArgumentUndoer(first, second);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/");
		return mv;
	}

	@PostMapping("/minus")
	public ModelAndView minusPage() {

		double first = stack.pop();
		double second = stack.pop();
		double result = second - first;
		stack.push(result);

		undoer = new TwoArgumentUndoer(first, second);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/");
		return mv;
	}

	@PostMapping("/undo")
	public ModelAndView undoPage() {

		undoer.undo(stack);
		undoer = null;

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/");
		return mv;
	}

}
