package com.hi.control;

import java.io.File;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hi.model.UploadItem;

@RequestMapping("/admin")
@Controller
public class UploadFileController {
	private static final Logger logger = LogManager
			.getLogger(UploadFileController.class);

	@RequestMapping(value = "/uploadfileindex", method = RequestMethod.GET)
	public ModelAndView showFileUpload(Locale locale, Model model) {
		return new ModelAndView("uploadfileindex", model.asMap());
	}

    @RequestMapping(value = "/uploadfile", method = RequestMethod.GET)
    public ModelAndView getUploadForm(Model model) {
            model.addAttribute(new UploadItem());
            return new ModelAndView("uploadfile", model.asMap());
    }
	
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public String create(UploadItem uploadItem, BindingResult result,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				logger.error("Error: " + error.getCode() + " - "
						+ error.getDefaultMessage());
			}
			return "/admin/uploadfile";
		}

		try {
			MultipartFile file = uploadItem.getFileData();
			String realPath = request.getSession().getServletContext()
					.getRealPath("/static/images/");
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(
					realPath, file.getOriginalFilename()));

			session.setAttribute("uploadFile", file.getOriginalFilename());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/uploadfileindex";
	}

}
