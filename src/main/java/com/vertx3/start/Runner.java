/**
*2016年12月11日, jim.huang, create
*/
package com.vertx3.start;

import java.io.File;
import java.io.IOException;

import com.hazelcast.util.function.Consumer;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * @Author jim.huang
 * @Date 2016年12月11日
 */
public class Runner {
	private static final String JAVA_DIR = "src/main/java/";

	public static void runClustered(final Class<?> clazz) {
		run(JAVA_DIR, clazz, new VertxOptions().setClustered(true), null);
	}

	public static void run(final Class<?> clazz) {
		run(JAVA_DIR, clazz, new VertxOptions().setClustered(false), null);
	}

	public static void run(final Class<?> clazz, final DeploymentOptions options) {
		run(JAVA_DIR, clazz, new VertxOptions().setClustered(false), options);
	}

	public static void run(final String exampleDir, final Class<?> clazz, final VertxOptions options,
			final DeploymentOptions deploymentOptions) {
		run(exampleDir + clazz.getPackage().getName().replace(".", "/"), clazz.getName(), options, deploymentOptions);
	}

	public static void runScript(final String prefix, final String scriptName, final VertxOptions options) {
		final File file = new File(scriptName);
		final String dirPart = file.getParent();
		final String scriptDir = prefix + dirPart;
		run(scriptDir, scriptDir + "/" + file.getName(), options, null);
	}

	public static void run(String scriptDir, final String verticleID, VertxOptions options,
			final DeploymentOptions deploymentOptions) {
		if (options == null) {
			// Default parameter
			options = new VertxOptions();
		}
		// Smart cwd detection

		// Based on the current directory (.) and the desired directory
		// (exampleDir), we try to compute the vertx.cwd
		// directory:
		try {
			// We need to use the canonical file. Without the file name is .
			final File current = new File(".").getCanonicalFile();
			if (scriptDir.startsWith(current.getName()) && !scriptDir.equals(current.getName())) {
				scriptDir = scriptDir.substring(current.getName().length() + 1);
			}
		} catch (final IOException e) {
			// Ignore it.
		}

		System.setProperty("vertx.cwd", scriptDir);
		final Consumer<Vertx> runner = vertx -> {
			try {
				if (deploymentOptions != null) {
					vertx.deployVerticle(verticleID, deploymentOptions);
				} else {
					vertx.deployVerticle(verticleID);
				}
			} catch (final Throwable t) {
				t.printStackTrace();
			}
		};
		if (options.isClustered()) {
			Vertx.clusteredVertx(options, res -> {
				if (res.succeeded()) {
					final Vertx vertx = res.result();
					runner.accept(vertx);
				} else {
					res.cause().printStackTrace();
				}
			});
		} else {
			final Vertx vertx = Vertx.vertx(options);
			runner.accept(vertx);
		}
	}
}
