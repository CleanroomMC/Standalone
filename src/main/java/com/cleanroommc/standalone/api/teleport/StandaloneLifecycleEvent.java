package com.cleanroommc.standalone.api.teleport;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;

public abstract class StandaloneLifecycleEvent extends Event {

    public abstract static class Config extends StandaloneLifecycleEvent {

        public static class Pre extends Config {

        }

        public static class Post extends Config {

        }

    }

    public static class PreInit extends StandaloneLifecycleEvent {

    }

    public abstract static class Init extends StandaloneLifecycleEvent {

        private final @Nonnull
        FMLInitializationEvent event;

        public @Nonnull
        FMLInitializationEvent getEvent() {
            return event;
        }

        public Init(@Nonnull FMLInitializationEvent event) {
            this.event = event;
        }

        public static class Pre extends Init {

            public Pre(@Nonnull FMLInitializationEvent event) {
                super(event);
            }

        }

        public static class Normal extends Init {

            public Normal(@Nonnull FMLInitializationEvent event) {
                super(event);
            }

        }

        public static class Post extends Init {

            public Post(@Nonnull FMLInitializationEvent event) {
                super(event);
            }

        }

    }

    public abstract static class PostInit extends StandaloneLifecycleEvent {

        public static class Pre extends PostInit {

        }

        public static class Post extends PostInit {

        }

    }

    public abstract static class ServerAboutToStart extends StandaloneLifecycleEvent {

        public static class Pre extends ServerAboutToStart {

        }

        public static class Post extends ServerAboutToStart {

        }

    }

    public abstract static class ServerStopped extends StandaloneLifecycleEvent {

        public static class Pre extends ServerStopped {

        }

        public static class Post extends ServerStopped {

        }

    }

    public static class ModIdMappingEvent extends StandaloneLifecycleEvent {

    }

    public abstract static class ServerStarting extends StandaloneLifecycleEvent {

        private final @Nonnull
        FMLServerStartingEvent event;

        public @Nonnull
        FMLServerStartingEvent getEvent() {
            return event;
        }

        public ServerStarting(@Nonnull FMLServerStartingEvent event) {
            this.event = event;
        }

        public static class Dedicated extends ServerStarting {

            /**
             * Call {@link com.cleanroommc.standalone.proxy.CommonProxy.getServerStartingEvent(FMLServerStartingEvent)} instead!
             */
            public Dedicated(@Nonnull FMLServerStartingEvent event) {
                super(event);
            }

        }

        public static class Integrated extends ServerStarting {

            /**
             * Call {@link com.cleanroommc.standalone.proxy.CommonProxy.getServerStartingEvent(FMLServerStartingEvent)} instead!
             */
            public Integrated(@Nonnull FMLServerStartingEvent event) {
                super(event);
            }

        }

    }
}