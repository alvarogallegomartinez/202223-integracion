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

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {
	@Mock
	private AuthDAO mockAuthDao;
	@Mock
	private GenericDAO mockGenericDao;

	@Test
	public void testStartRemoteSystemWithInvalidUserAndSystem() throws Exception {
		when(mockAuthDao.getAuthData("1")).thenReturn(null);

		String remoteId = "12345";
		when(mockGenericDao.getSomeData(null, "where id=" + remoteId)).thenThrow(OperationNotSupportedException.class);

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class,() -> manager.startRemoteSystem("1", remoteId));

		ordered.verify(mockAuthDao).getAuthData("1");
		ordered.verify(mockGenericDao).getSomeData(null, "where id=" + remoteId);
	}

	@Test
	public void testStopRemoteSystemWithInvalidUserAndSystem() throws Exception {
		when(mockAuthDao.getAuthData("1")).thenReturn(null);

		String remoteId = "12345";
		when(mockGenericDao.getSomeData(null, "where id=" + remoteId)).thenThrow(OperationNotSupportedException.class);

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class,() -> manager.stopRemoteSystem("1", remoteId));

		ordered.verify(mockAuthDao).getAuthData("1");
		ordered.verify(mockGenericDao).getSomeData(null, "where id=" + remoteId);
	}

	@Test
	public void testAddRemoteSystemWithInvalidUserAndSystem() throws Exception {
		when(mockAuthDao.getAuthData("1")).thenReturn(null);

		ArrayList<Object> remote = new ArrayList<>(Arrays.asList("uno", "dos"));
		when(mockGenericDao.updateSomeData(null, remote)).thenThrow(OperationNotSupportedException.class);

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertThrows(SystemManagerException.class,() -> manager.addRemoteSystem("1", remote));

		ordered.verify(mockAuthDao).getAuthData("1");
		ordered.verify(mockGenericDao).updateSomeData(null, remote);
	}

	@Test
	public void testDeleteRemoteSystemWithInvalidUserAndSystem() throws Exception {
		when(mockAuthDao.getAuthData("1")).thenReturn(null);
		String remoteId = "12345";
		lenient().when(mockGenericDao.deleteSomeData(null, remoteId)).thenThrow(OperationNotSupportedException.class);

		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		

		assertThrows(SystemManagerException.class,() -> manager.deleteRemoteSystem("1", remoteId));
		
		ordered.verify(mockAuthDao).getAuthData("1");
		ordered.verify(mockGenericDao).deleteSomeData(null, remoteId);
	}
	
}
