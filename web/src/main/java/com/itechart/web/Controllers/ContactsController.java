package com.itechart.web.Controllers;
import com.itechart.model.dao.ContactDaoImpl;
import com.itechart.model.dto.ContactDto;
import com.itechart.model.services.ContactsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
//@RequestMapping("/contactsLipgart/contacts")
@RequestMapping("/contacts")
public class ContactsController {

    ContactDaoImpl contactDao = new ContactDaoImpl();
    ContactsService service = new ContactsService();


    @GetMapping("/count")
    public int getRecordsCount(){
        return contactDao.countAll();
    }


    @PostMapping("/search/count")
    public int getRecordsCountByParams(@RequestBody HashMap<String, String> params){
        return contactDao.countAllByParams(params);
    }

    @GetMapping(params = {"page", "size" })
    public List<ContactDto> getContacts(@RequestParam( "page" ) int page,
                                        @RequestParam( "size" ) int size){

        return contactDao.readAll(page, size);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContacts(@RequestBody List<Long> contactIdList){
        service.delete(contactIdList);
    }



    @PostMapping("/search")
    public List<ContactDto> searchContacts(@RequestBody HashMap<String, String> params,
                                           @RequestParam( "page" ) int page,
                                           @RequestParam( "size" ) int size){
        List<ContactDto> result = contactDao.readAllByParams(params, page, size);
        return result;
    }

}
