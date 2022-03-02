package cn.rg.cems.login.service;

import cn.rg.cems.common.entity.Account;
import cn.rg.cems.common.entity.Examinee;
import cn.rg.cems.login.mapper.AccountMapper;
import cn.rg.cems.login.mapper.ExamineeMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountService implements UserDetailsService {

    @Resource
    private AccountMapper accountMapper;
    @Resource
    private ExamineeMapper examineeMapper;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account;
        if (username.indexOf('@') == -1)
            account = accountMapper.findByPhone(username);
        else
            account = accountMapper.findByEmail(username);
        if (account == null)
            throw new UsernameNotFoundException("Account does not exist");
        account.setRole(accountMapper.findRolesById(account.getId()));
        return account;
    }

    /**
     * 注册账号
     *
     * @param examinee 考生信息
     * @return 受影响行数
     */
    public Integer register(Examinee examinee) {
        Account account = examinee.getAccount();
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setLock(true);
        accountMapper.addItem(account);
        examinee.setAccountId(account.getId());
        return examineeMapper.add(examinee);
    }
}
