package com.maxdev.warehouse.controllers;

import com.maxdev.warehouse.exceptions.NotFindException;
import com.maxdev.warehouse.exceptions.UnsafeOperationException;
import com.maxdev.warehouse.exceptions.WelcomeToQueueException;
import com.maxdev.warehouse.models.*;
import com.maxdev.warehouse.repo.*;
import com.maxdev.warehouse.security.AuthManager;
import com.maxdev.warehouse.security.Authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maxdev.warehouse.exceptions.AccessDeniedException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * Roles:
 *      A - get from warehouse
 *      B - put to warehouse
 *      C - check the journals
 *      D - change users roles
 */

@RestController
@RequestMapping("/app")
@Tag(name = "AppController", description = "Rest controller on warehouse manager project")
public class AppController {
    @Autowired
    CredentialsRepository cr;
    @Autowired
    UsercardsRepository ucr;
    @Autowired
    GoodsRepository gr;
    @Autowired
    RacksRepository rr;
    @Autowired
    OutcomingRepository or;
    @Autowired
    IncomingRepository ir;
    @Autowired
    private AuthManager authManager;


    @GetMapping("/profile")
    @Operation(summary = "Getting authenticated profile usercard")
    public Usercard profile(HttpServletRequest request) {
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null)
            return cr.findCredentialByEmail(
                    auth.getLogin()
            ).getUsercard();
        else
            throw new AccessDeniedException("get profile information", true);
    }

    @PutMapping("/profile")
    @Operation(summary = "Updating authenticated profile userecard")
    public Usercard profile(@RequestBody Usercard usercard, HttpServletRequest request) {
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null) {
            Usercard uc = cr.findCredentialByEmail(
                    auth.getLogin()
            ).getUsercard();
            uc.setLastname(usercard.getLastname());
            uc.setFirstname(usercard.getFirstname());
            uc.setBirthday(usercard.getBirthday());
            ucr.save(uc);
            return uc;
        } else
            throw new AccessDeniedException("put profile information", true);
    }

    @GetMapping("/profile/{id}")
    @Operation(summary = "Get usercard of user with id(in credentials table)")
    public Usercard profile(@PathVariable Integer id, HttpServletRequest request) {
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().equals("ABCD")) {
            return ucr.findById(id).orElseThrow(() -> new NotFindException("profiles with this id"));
        } else {
            throw new AccessDeniedException("get all profiles information", auth == null);
        }
    }

    @PutMapping("/profile/{id}")
    @Operation(summary = "Change usercard of user with id(in credentials table)")
    public Usercard fillUsercard(@RequestBody Usercard usercard, @PathVariable Integer id, HttpServletRequest request) {
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().charAt(3) == 'D') {
            return ucr.findById(id)
                    .map(uc -> {
                        uc.setFirstname(usercard.getFirstname());
                        uc.setLastname(usercard.getLastname());
                        uc.setBirthday(usercard.getBirthday());
                        uc.setRoles(usercard.getRoles());
                        return ucr.save(uc);
                    })
                    .orElseThrow(() -> new NotFindException("profiles with this id"));
        } else {
            throw new AccessDeniedException("get all profiles information", auth == null);
        }
    }

    @GetMapping("/profiles")
    @Operation(summary = "Getting all profiles usercards(info), request sender has to have all roles")
    public List<Usercard> profiles(HttpServletRequest request) throws AccessDeniedException {
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().equals("ABCD")) {
            return ucr.findAll();
        } else {
            throw new AccessDeniedException("get all profiles information", auth == null);
        }
    }


    @GetMapping("/goods")
    @Operation(description = "Getting all goods information")
    public List<Good> goods(HttpServletRequest request) {
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null) {
            return gr.findAll();
        } else {
            throw new AccessDeniedException("get all goods", true);
        }
    }

    @GetMapping("/good-info/{id}")
    @Operation(summary = "Getting general info about good with id")
    public Good good(@PathVariable Integer id, HttpServletRequest request) {
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null) {
            return gr.findById(id).orElseThrow(() -> new NotFindException("goods with this id"));
        } else {
            throw new AccessDeniedException("get good's information", auth == null);
        }
    }

    @PostMapping("/good-info/{id}")
    @Operation(summary = "DEPRECATED!Manual adding good in goods table")
    public Good newGood(@RequestBody Good newGood, @PathVariable Integer id, HttpServletRequest request) {
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().charAt(1) == 'B') {
            return gr.findById(id)
                    .map(good -> {
                        good.setName(newGood.getName());
                        good.setDescr(newGood.getDescr());
                        good.setTotalnumber(newGood.getTotalnumber());
                        return gr.save(good);
                    })
                    .orElseThrow(() -> new NotFindException("goods with this id"));
        } else {
            throw new AccessDeniedException("add good's information", auth == null);
        }

    }

    @PutMapping("/good-info/{id}")
    @Operation(summary = "DEPRECATED!Manual changing good in goods table")
    public Good changeGood(@RequestBody Good newGood, @PathVariable Integer id, HttpServletRequest request) {
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().charAt(1) == 'B') {
            return gr.findById(id)
                    .map(good -> {
                        good.setName(newGood.getName());
                        good.setDescr(newGood.getDescr());
                        good.setTotalnumber(newGood.getTotalnumber());
                        return gr.save(good);
                    })
                    .orElseThrow(() -> new NotFindException("goods with this id"));
        } else {
            throw new AccessDeniedException("change good's information", auth == null);
        }

    }

    @DeleteMapping("/good-info/{id}")
    @Operation(summary = "DEPRECATED!Manual deleting good in goods table")
    public void delGood(@PathVariable Integer id, HttpServletRequest request) {
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().charAt(1) == 'B') {
            gr.deleteById(id);
        } else {
            throw new AccessDeniedException("delete good's information", auth == null);
        }

    }
    @GetMapping("/good/{id}")
    @Operation(summary = "getting good from racks"
    ,description = "searching all rack associated with this good and sequentially get from each \n" +
            "til they run out or the requested amount is reached")
    public Good getGood(@PathVariable Integer id, @RequestParam(value="number") Integer _number,HttpServletRequest request){
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if(auth!=null&&auth.getRole().charAt(0)=='A') {
            Good good = gr.findById(id).orElseThrow(() -> new NotFindException("goods with this id"));
            int number = _number;
            if (number > good.getTotalnumber()) {
                or.save(new Outcoming(  //make record that query unsatisfied
                        cr.findCredentialByEmail(auth.getLogin()).getUsercard(),
                        new Timestamp(System.currentTimeMillis()),
                        null,
                        good,
                        number,
                        false
                ));
                throw new WelcomeToQueueException("havent enough good with id: " + id);
            } else {
                List<Rack> racks = rr.findAllByGoodId(id);
                for (Rack rack : racks) {
                    int rackNumber = rack.getNumber();
                    if (rackNumber >= number) {
                        rack.setNumber(rackNumber - number);
                        rr.save(rack);
                        good.setTotalnumber(good.getTotalnumber() - number);
                        gr.save(good);
                        or.save(new Outcoming(  //make record that query satisfied
                                cr.findCredentialByEmail(auth.getLogin()).getUsercard(),
                                new Timestamp(System.currentTimeMillis()),
                                rack,
                                good,
                                number,
                                true
                        ));
                        return new Good(_number,good.getName(),good.getDescr());
                    } else {
                        rack.setNumber(0);
                        rr.save(rack);
                        good.setTotalnumber(good.getTotalnumber()-rackNumber);
                        number-=rackNumber;
                        or.save(new Outcoming(  //make record that query satisfied
                                cr.findCredentialByEmail(auth.getLogin()).getUsercard(),
                                new Timestamp(System.currentTimeMillis()),
                                rack,
                                good,
                                rackNumber,
                                true
                        ));
                    }

                }
            }
        } else{
            throw new AccessDeniedException("get good", auth == null);
        }
        throw new NotFindException("internal problem");
    }
    @GetMapping("/racks")
    @Operation(summary = "get info about all racks")
    public List<Rack> racks(HttpServletRequest request){
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().charAt(1)=='B') {
            return rr.findAll();
        }else {
            throw new AccessDeniedException("get rack's information", auth == null);
        }
    }

    @PostMapping("/rack")
    @Operation(summary = "set up new rack with address")
    public void createRack(@RequestParam(value="addr") String addr, HttpServletRequest request){
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().charAt(1)=='B') {
            Rack rack = new Rack();
            rack.setAddr(addr);
            rr.save(rack);
        }else {
            throw new AccessDeniedException("set up new rack", auth == null);
        }
    }
    @DeleteMapping("/rack")
    @Operation(summary = "delete rack by address")
    public Rack delRack(@RequestParam(value="addr") String addr, HttpServletRequest request){
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().charAt(1)=='B') {
            Rack rack = rr.findByAddr(addr);
            if (rack.getNumber() != 0) {
                throw new UnsafeOperationException("Make sure that rack you want to delete is empty");
            }
            rr.deleteById(rack.getId());
            return rack;
        }else {
            throw new AccessDeniedException("delete rack information", auth == null);
        }
    }
    @GetMapping("/racks/{id}")
    @Operation(summary = "get info about all racks binded on good with id")
    public List<Rack> goodRacks(@PathVariable(value = "id") int id, HttpServletRequest request){
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null) {
            return rr.findAllByGoodId(id);
        }else {
            throw new AccessDeniedException("get all racks binded on good with id", auth == null);
        }
    }

    @GetMapping("/logs")
    @Operation(summary = "get all logs")
    public List<LogEntry> logs(HttpServletRequest request){
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().charAt(2)=='C') {
            List<LogEntry> res = new ArrayList<LogEntry>(or.findAll());
//            res.addAll((List<? extends  LogEntry>)ir.findAll());
            res.addAll(ir.findAll());
            return res;
        }else {
            throw new AccessDeniedException("get all logs", auth == null);
        }
    }
    @GetMapping("/queue")
    @Operation(summary = "get all queue logs")
    public List<Outcoming> queue(HttpServletRequest request){
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().charAt(2)=='C') {
            return or.findAllBySatisfiedFalse();
        }else {
            throw new AccessDeniedException("get queue logs", auth == null);
        }
    }
    @PutMapping("/bind-rack")
    @Operation(summary = "Bind rack on good with id")
    public void bindRack(@RequestParam(value="id") int id, @RequestParam(value="addr") String addr, HttpServletRequest request){
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().charAt(1)=='B' && auth.getRole().charAt(3)=='D') {
            Rack rack = rr.findByAddr(addr);
            if(rack.getNumber()!=0)
                throw new UnsafeOperationException("Make sure that rack you want to bind is empty");
            rack.setGood(gr.findById(id).orElseThrow(() -> new NotFindException("goods with this id")));
        }else {
            throw new AccessDeniedException("bind rack on good", auth == null);
        }
    }
    @GetMapping("/good")
    @Operation(summary = "Get number of good from rack with address")
    public Good getGoodByAddr(@RequestParam(value="addr") String addr,@RequestParam(value="number") int number, HttpServletRequest request){
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().charAt(0)=='A') {
            Rack rack = rr.findByAddr(addr);
            Good good = rack.getGood();
            boolean satisfied = true;
            int delta = rack.getNumber() - number;
            if(delta<0){
                satisfied = false;
            } else{
                rack.setNumber(delta);
                rr.save(rack);
                good.setTotalnumber(good.getTotalnumber()-number);
                gr.save(good);
            }
            or.save(new Outcoming(  //make record that query satisfied or not
                    cr.findCredentialByEmail(auth.getLogin()).getUsercard(),
                    new Timestamp(System.currentTimeMillis()),
                    rack,
                    good,
                    number,
                    satisfied
            ));
            return good;
        }else {
            throw new AccessDeniedException("get good from rack", auth == null);
        }
    }
    @PutMapping("/good")
    @Operation(summary = "Put number of good to rack with address")
    public void putGoodByAddr(@RequestParam(value="addr") String addr,@RequestParam(value="number") int number, HttpServletRequest request){
        String ra = request.getRemoteAddr();
        Authentication auth = authManager.checkSession(ra);
        if (auth != null && auth.getRole().charAt(1)=='B') {
            Rack rack = rr.findByAddr(addr);
            Good good = rack.getGood();
            rack.setNumber(rack.getNumber()+number);
            good.setTotalnumber(good.getTotalnumber()+number);
            rr.save(rack);
            gr.save(good);
            ir.save(new Incoming(  //make record that query satisfied or not
                    cr.findCredentialByEmail(auth.getLogin()).getUsercard(),
                    new Timestamp(System.currentTimeMillis()),
                    rack,
                    good,
                    number,
                    true
            ));
        }else {
            throw new AccessDeniedException("put good to rack", auth == null);
        }
    }



}
