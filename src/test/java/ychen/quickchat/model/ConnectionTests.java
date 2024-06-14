package ychen.quickchat.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ConnectionTests {
    private Connection connection;

    @Before
    public void setUp() {
        connection = new Connection(1L, 2L, Connection.ConnectionStatus.PENDING);
    }

    @Test
    public void testCreateConnection() {
        assertNotNull(connection);
        assertEquals(Long.valueOf(1), connection.getUserId());
        assertEquals(Long.valueOf(2), connection.getFriendId());
        assertEquals(Connection.ConnectionStatus.PENDING, connection.getStatus());
        assertNotNull(connection.getCreatedAt());
        assertNotNull(connection.getUpdatedAt());

        long tolerance = 100; // milliseconds tolerance
        long timeDiff = Math.abs(connection.getCreatedAt().toInstant(ZoneOffset.UTC).toEpochMilli() -
                connection.getUpdatedAt().toInstant(ZoneOffset.UTC).toEpochMilli());
        assertTrue("Time difference too large: " + timeDiff, timeDiff <= tolerance);
    }

    @Test
    public void testUpdateConnection() {
        LocalDateTime oldUpdatedAt = connection.getUpdatedAt();

        connection.setUpdatedAt(LocalDateTime.now());

        long tolerance = 100; // milliseconds tolerance for timing discrepancies
        long timeDiff = Math.abs(oldUpdatedAt.toInstant(ZoneOffset.UTC).toEpochMilli() -
                connection.getUpdatedAt().toInstant(ZoneOffset.UTC).toEpochMilli());
        assertTrue("Time difference too large after update: " + timeDiff, timeDiff <= tolerance);
    }

    @Test
    public void testConnectionStatusEnum() {
        connection.setStatus(Connection.ConnectionStatus.BLOCKED);
        assertEquals(Connection.ConnectionStatus.BLOCKED, connection.getStatus());
    }
}
