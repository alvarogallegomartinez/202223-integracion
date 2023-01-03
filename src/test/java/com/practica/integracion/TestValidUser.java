package com.practica.integracion;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestValidUser {
    @Mock
    private AuthDAO mockAuthDao;
    @Mock
    private GenericDAO mockGenericDao;
    @Test
    public void testStartRemoteSystemWithValidUserAndSystem() throws Exception {
        User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<>(Arrays.asList(1, 2)));
        when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

        String remoteId = "12345";
        ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
        when(mockGenericDao.getSomeData(validUser, "where id=" + remoteId)).thenReturn(lista);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        Collection<Object> retorno = manager.startRemoteSystem(validUser.getId(), remoteId);
        assertEquals(retorno.toString(), "[uno, dos]");
        ordered.verify(mockAuthDao).getAuthData(validUser.getId());
        ordered.verify(mockGenericDao).getSomeData(validUser, "where id=" + remoteId);
    }
    
    @Test
    public void testStopRemoteSystemWithValidUserAndSystem() throws Exception {
        User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<>(Arrays.asList(1, 2)));
        when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

        String remoteId = "12345";
        ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
        when(mockGenericDao.getSomeData(validUser, "where id=" + remoteId)).thenReturn(lista);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        Collection<Object> retorno = manager.stopRemoteSystem(validUser.getId(), remoteId);
        assertEquals(retorno.toString(), "[uno, dos]");
        ordered.verify(mockAuthDao).getAuthData(validUser.getId());
        ordered.verify(mockGenericDao).getSomeData(validUser, "where id=" + remoteId);
    }

    @Test
    public void testAddRemoteSystemWithValidUserAndSystem1() throws Exception {
        User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<>(Arrays.asList(1, 2)));
        when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

        ArrayList<Object> remote = new ArrayList<>(Arrays.asList("uno", "dos"));
        when(mockGenericDao.updateSomeData(validUser, remote)).thenReturn(true);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        assertDoesNotThrow(() -> manager.addRemoteSystem(validUser.getId(), remote));

        ordered.verify(mockAuthDao).getAuthData(validUser.getId());
        ordered.verify(mockGenericDao).updateSomeData(validUser, remote);
    }

    @Test
    public void testAddRemoteSystemWithValidUserAndSystem2() throws Exception {
        User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<>(Arrays.asList(1, 2)));
        when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

        ArrayList<Object> remote = new ArrayList<>(Arrays.asList("uno", "dos"));
        when(mockGenericDao.updateSomeData(validUser, remote)).thenReturn(false);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        Exception ex = assertThrows(SystemManagerException.class,() -> manager.addRemoteSystem(validUser.getId(), remote));
        assertEquals(ex.getMessage(), "cannot add remote");

        ordered.verify(mockAuthDao).getAuthData(validUser.getId());
        ordered.verify(mockGenericDao).updateSomeData(validUser, remote);
    }

    @Test
    public void testDeleteRemoteSystemWithValidUserAndSystem1() throws Exception {
        User validUser = new User("1", "Antonio", "Perez", "Madrid", new ArrayList<Object>());
        when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

        String remoteId = "12345";
        lenient().when(mockGenericDao.deleteSomeData(validUser, remoteId)).thenReturn(true);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        assertDoesNotThrow(() -> manager.deleteRemoteSystem(validUser.getId(), remoteId));
        
        ordered.verify(mockAuthDao).getAuthData(validUser.getId());
        ordered.verify(mockGenericDao).deleteSomeData(validUser, remoteId);
    }
    @Test
    public void testDeleteRemoteSystemWithValidUserAndSystem2() throws Exception {
        User validUser = new User("1", "Antonio", "Perez", "Madrid", new ArrayList<Object>());
        when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
        
        String remoteId = "12345";
        lenient().when(mockGenericDao.deleteSomeData(validUser, remoteId)).thenReturn(false);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        Exception ex = assertThrows(SystemManagerException.class,() -> manager.deleteRemoteSystem(validUser.getId(), remoteId));
        assertEquals(ex.getMessage(), "cannot delete remote: does remote exists?");
        
        ordered.verify(mockAuthDao).getAuthData(validUser.getId());
        ordered.verify(mockGenericDao).deleteSomeData(validUser, remoteId);
    }

    @Test
    public void testDeleteRemoteSystemWithValidUserAndSystem3() throws Exception {
        User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<>(Arrays.asList(1, 2)));
        when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
        
        String remoteId = "12345";
        lenient().when(mockGenericDao.deleteSomeData(validUser, remoteId)).thenReturn(true);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        assertDoesNotThrow(() -> manager.deleteRemoteSystem(validUser.getId(), remoteId));
        
        ordered.verify(mockAuthDao).getAuthData(validUser.getId());
        ordered.verify(mockGenericDao).deleteSomeData(validUser, remoteId);
    }
    @Test
    public void testDeleteRemoteSystemWithValidUserAndSystem4() throws Exception {
        User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<>(Arrays.asList(1, 2)));
        when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
        
        String remoteId = "12345";
        lenient().when(mockGenericDao.deleteSomeData(validUser, remoteId)).thenReturn(false);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        Exception ex = assertThrows(SystemManagerException.class,() -> manager.deleteRemoteSystem(validUser.getId(), remoteId));
        assertEquals(ex.getMessage(), "cannot delete remote: does remote exists?");
        
        ordered.verify(mockAuthDao).getAuthData(validUser.getId());
        ordered.verify(mockGenericDao).deleteSomeData(validUser, remoteId);
    }
}
