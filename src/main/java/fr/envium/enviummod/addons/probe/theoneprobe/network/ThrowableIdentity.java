package fr.envium.enviummod.addons.probe.theoneprobe.network;

import fr.envium.enviummod.addons.probe.theoneprobe.TheOneProbe;
import fr.envium.enviummod.addons.probe.theoneprobe.config.Config;

import java.util.HashMap;
import java.util.Map;

public class ThrowableIdentity {
    private final String identifier;

    private static final Map<ThrowableIdentity, Long> catchedThrowables = new HashMap<>();

    public static void registerThrowable(Throwable e) {
        ThrowableIdentity identity = new ThrowableIdentity(e);
        long curtime = System.currentTimeMillis();
        if (catchedThrowables.containsKey(identity)) {
            long lasttime = catchedThrowables.get(identity);
            if (curtime < lasttime + Config.loggingThrowableTimeout.get()) {
                // If this exception occured less then some time ago we don't report it.
                return;
            }
        }
        catchedThrowables.put(identity, curtime);
        TheOneProbe.logger.debug("The One Probe catched error: ", e);
    }

    public ThrowableIdentity(Throwable e) {
        String message = e.getMessage();
        StringBuilder builder = new StringBuilder(message == null ? "<null>" : message);
        StackTraceElement[] st = e.getStackTrace();
        for (int i = 0; i < Math.min(3, st.length); i++) {
            builder
                    .append(st[i].getClassName())
                    .append(st[i].getFileName())
                    .append(st[i].getMethodName())
                    .append(st[i].getLineNumber());
        }
        identifier = builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThrowableIdentity that = (ThrowableIdentity) o;

        return identifier != null ? identifier.equals(that.identifier) : that.identifier == null;
    }

    @Override
    public int hashCode() {
        return identifier != null ? identifier.hashCode() : 0;
    }
}
