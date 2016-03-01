module.exports = function(grunt) {

  grunt.initConfig({

    // JS TASKS ================================================================
    jshint: {
      all: ['FrontEnd/src/js/**/*.js'] 
    },

    uglify: {
      build: {
        files: {
          'FrontEnd/dist/js/app.min.js': ['FrontEnd/src/js/**/*.js', 'FrontEnd/src/js/*.js']
        }
      }
    },

    // CSS TASKS ===============================================================
    less: {
      build: {
        files: {
          'FrontEnd/dist/css/style.css': 'FrontEnd/src/css/style.less'
        }
      }
    },

    cssmin: {
      build: {
        files: {
          'FrontEnd/dist/css/style.min.css': 'FrontEnd/dist/css/style.css'
        }
      }
    },

    // COOL TASKS ==============================================================
    watch: {
      css: {
        files: ['FrontEnd/src/css/**/*.less'],
        tasks: ['less', 'cssmin']
      },
      js: {
        files: ['FrontEnd/src/js/**/*.js'],
        tasks: ['jshint', 'uglify']
      }
    },

    run: {
      options: {
        // Task-specific options go here. 
      },
      your_target: {
        cmd: './run.sh',
        args: []
      }
    },
    concurrent: {
      options: {
        logConcurrentOutput: true
      },
      tasks: ['run:your_target', 'watch']
    }   

  });

  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-concurrent');
  grunt.loadNpmTasks('grunt-run');

  grunt.registerTask('default', ['less', 'cssmin', 'jshint', 'uglify', 'concurrent']);

};