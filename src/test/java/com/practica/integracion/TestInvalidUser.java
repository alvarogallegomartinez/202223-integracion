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
	public void testAddRemoteSystemWithInvalidUserAndSystem1() throws Exception {
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
	public void testAddRemoteSystemWithInvalidUserAndSystem2() throws Exception {
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
	public void testDeleteRemoteSystemWithInvalidUserAndSystem1() throws Exception {
		User validUser = new User("1", "Antonio", "Perez", "Madrid", new ArrayList<Object>());

		String remoteId = "12345";
		lenient().when(mockGenericDao.deleteSomeData(validUser, remoteId)).thenReturn(true);

		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertDoesNotThrow(() -> manager.deleteRemoteSystem(validUser.getId(), remoteId));
	}
	@Test
	public void testDeleteRemoteSystemWithInvalidUserAndSystem2() throws Exception {
		User validUser = new User("1", "Antonio", "Perez", "Madrid", new ArrayList<Object>());

		String remoteId = "12345";
		lenient().when(mockGenericDao.deleteSomeData(validUser, remoteId)).thenReturn(false);

		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		Exception ex = assertThrows(SystemManagerException.class,() -> manager.deleteRemoteSystem(validUser.getId(), remoteId));
		assertEquals(ex.getMessage(), "cannot delete remote: does remote exists?");
	}

	@Test
	public void testDeleteRemoteSystemWithInvalidUserAndSystem3() throws Exception {
		User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<>(Arrays.asList(1, 2)));

		String remoteId = "12345";
		lenient().when(mockGenericDao.deleteSomeData(validUser, remoteId)).thenReturn(true);

		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		assertDoesNotThrow(() -> manager.deleteRemoteSystem(validUser.getId(), remoteId));
	}
	@Test
	public void testDeleteRemoteSystemWithInvalidUserAndSystem4() throws Exception {
		User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<>(Arrays.asList(1, 2)));

		String remoteId = "12345";
		lenient().when(mockGenericDao.deleteSomeData(validUser, remoteId)).thenReturn(false);

		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		Exception ex = assertThrows(SystemManagerException.class,() -> manager.deleteRemoteSystem(validUser.getId(), remoteId));
		assertEquals(ex.getMessage(), "cannot delete remote: does remote exists?");
	}
}
